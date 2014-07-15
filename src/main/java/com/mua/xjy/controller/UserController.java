package com.mua.xjy.controller;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.baidu.yun.core.json.JSONParser;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mua.xjy.dto.FriendDto;
import com.mua.xjy.dto.FriendVo;
import com.mua.xjy.dto.UserDto;
import com.mua.xjy.service.FriendService;
import com.mua.xjy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;

    @RequestMapping(method = RequestMethod.POST, value = "/user/login")
    @ResponseBody
    public UserDto login(String username, String password){
        return userService.login(username, password);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/register")
    @ResponseBody
    public UserDto register(String username, String password,String channelId, String userId,
                            String name){
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setChannelId(channelId);
        userDto.setUserId(userId);
        userDto.setLevel(0);
        userDto.setName(name);
        int id = userService.add(userDto);
        return userService.load(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/friend")
    @ResponseBody
    public List<FriendVo> friend(int userId){
        return friendService.list(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/addFriend")
    @ResponseBody
    public UserDto friend(int userId, int friendId){
        FriendDto friendDto = new FriendDto();
        friendDto.setUserId(userId);
        friendDto.setFriendId(friendId);
        friendDto.setStatus(0);
        friendDto.setSpecialName(null);
        return friendService.add(friendDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/app")
    @ResponseBody
    public String app(int id, String channelId, String userId){
        System.out.println(id);
        UserDto userDto = userService.load(id);
        if(userDto != null) {
            userDto.setChannelId(channelId);
            userDto.setUserId(userId);
            if(!userService.update(userDto)){
                return "fail";
            }
            return "success";
        } else {
            return "fail";
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/mua")
    @ResponseBody
    public String mua(String userId, String friendId){
        UserDto userDto = userService.load(Integer.parseInt(userId));
        UserDto friendDto = userService.load(Integer.parseInt(friendId));
        /*
         * @brief 推送单播消息(消息类型为透传，由开发方应用自己来解析消息内容) message_type = 0 (默认为0)
         */

        // 1. 设置developer平台的ApiKey/SecretKey
        String apiKey = "vkuBbc45CzsUXAxUyGfk5ZZi";
        String secretKey = "MUpsQKygLZzXu7G2UzwRXXfoxQMnbNdl";
        ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);

        // 2. 创建BaiduChannelClient对象实例
        BaiduChannelClient channelClient = new BaiduChannelClient(pair);

        // 3. 若要了解交互细节，请注册YunLogHandler类
        channelClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });
        try {

            // 4. 创建请求类对象
            // 手机端的ChannelId， 手机端的UserId， 先用1111111111111代替，用户需替换为自己的
            PushUnicastMessageRequest request = new PushUnicastMessageRequest();
            request.setDeviceType(3); // device_type => 1: web 2: pc 3:android
            // 4:ios 5:wp
            request.setChannelId(Long.parseLong(friendDto.getChannelId()));
            request.setUserId(friendDto.getUserId());
            Gson gson = new Gson();

            request.setMessage(gson.toJson(userDto));

            // 5. 调用pushMessage接口
            PushUnicastMessageResponse response = channelClient
                    .pushUnicastMessage(request);

            // 6. 认证推送成功
            System.out.println("push amount : " + response.getSuccessAmount());

        } catch (ChannelClientException e) {
            // 处理客户端错误异常
            e.printStackTrace();
        } catch (ChannelServerException e) {
            // 处理服务端错误异常
            System.out.println(String.format(
                    "request_id: %d, error_code: %d, error_message: %s",
                    e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
        }
        return "success";
    }
}
