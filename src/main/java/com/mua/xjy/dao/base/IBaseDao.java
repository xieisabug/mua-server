package com.mua.xjy.dao.base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xjyaikj on 2014/7/3.
 */
public interface IBaseDao<T> {
    public Serializable add(T t) throws Exception;
    public void delete(int id) throws Exception;
    public boolean update(T t) throws Exception;
    public T load(Serializable id) throws Exception;
    public T get(Serializable id) throws Exception;
    public List<T> list(String hql,Object[] args) throws Exception;
    public List<T> list(String hql) throws Exception;
    public List<T> list(String hql,Object arg) throws Exception;
//    public List<T> complexList(String hql) throws Exception;
    //public List<T> queryForPage(final String hql,final int offset,final int length)throws Exception;


    public Pager<T> find(String hql, Object arg,int pageOffset,int pageSize) throws Exception;

    public Pager<T> find(String hql,int pageOffset,int pageSize) throws Exception;

    public Pager<T> find(String hql, Object[] args,int pageOffset,int pageSize) throws Exception;
}
