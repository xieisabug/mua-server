package com.mua.xjy.dao.base;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 通用的BaseDao，用于继承
 */
public class BaseDao<T> implements IBaseDao<T> {

    /**
     * 此处不能使用setSessionFactory注入，因为setSessionFactory在HibernateDaoSupport
     * 中已经定义了而且还是final的，所以不能被覆盖
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 创建一个Class的对象来获取泛型的class
     */
    private Class<T> clz;

    @SuppressWarnings("unchecked")
    public Class<T> getClz() {
        if (clz == null) {
            // 获取泛型的Class对象
            clz = ((Class<T>) (((ParameterizedType) (this.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[0]));
        }
        return clz;
    }

    /**
     * 保存实例
     *
     * @param t 实例
     * @throws HibernateException
     */
    public Serializable add(T t) throws HibernateException {
        Session session=null;
        Serializable id = -1;
        try {
            session=sessionFactory.openSession();
            session.beginTransaction();
            id = session.save(t);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new HibernateException(e);
        }finally{
            if (session != null) {
                session.close();
            }
        }
        return id;
    }

    /**
     * 修改实例
     *
     * @param t 实例
     * @throws HibernateException
     */
    public boolean update(T t) throws HibernateException{
        Session session=null;
        try {
            session=sessionFactory.openSession();
            session.beginTransaction();
            session.update(t);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }finally{
            if (session != null) {
                session.close();
            }
        }
        return true;
    }

    /**
     * 删除实例
     *
     * @param id 删除的id
     * @throws HibernateException
     */
    public void delete(int id) throws HibernateException{
        Session session=null;
        try {
            session=sessionFactory.openSession();
            session.beginTransaction();
            session.delete(this.load(id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new HibernateException(e);
        } finally{
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * 获取实例
     *
     * @param id 获取的id
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    public T load(Serializable id) throws HibernateException{
        Session session=null;
        T t=null;
        try {
            session=sessionFactory.openSession();
            session.beginTransaction();
            t=(T) session.load(getClz(), id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new HibernateException(e);
        }finally{
            if (session != null) {
                session.close();
            }
        }
        return t;
    }

    /**
     * 获取实例
     *
     * @param id 获取的id
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    public T get(Serializable id) throws Exception{
        Session session=null;
        T t=null;
        try {
            session=sessionFactory.openSession();
            session.beginTransaction();
            t=(T) session.get(getClz(), id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new HibernateException(e);
        }finally{
            if (session != null) {
                session.close();
            }
        }
        return t;
    }

    @SuppressWarnings("unchecked")
    public List<T> list(String hql, Object[] args) throws Exception {
        Session session = null;
        List<T> list = null;
        try {
            session = sessionFactory.openSession();
            Query u = session.createQuery(hql);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    u.setParameter(i, args[i]);
                }
            }
            list = u.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new HibernateException(e);
        }finally{
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    public List<T> list(String hql) throws Exception {
        return this.list(hql, null);
    }

    public List<T> list(String hql, Object arg) throws Exception {
        return this.list(hql, new Object[]{arg});
    }


        /*public List<T> queryForPage(final String hql, final int offset,
                        final int length) throws Exception {
                List list = this.getHibernateTemplate().executeFind(
                                new HibernateCallback() {
                                        public T doInHibernate(Session session)
                                                        throws HibernateException, SQLException {
                                                Query query = session.createQuery(hql);
                                                query.setFirstResult(offset);
                                                query.setMaxResults(length);
                                                List list = query.list();
                                                return (T) list;
                                        }

                                });
                return list;
        }*/


    public Pager<T> find(String hql, Object arg, int pageOffset, int pageSize) throws Exception {
        return this.find(hql, new Object[]{arg}, pageOffset, pageSize);
    }

    public Pager<T> find(String hql, int pageOffset, int pageSize) throws Exception {
        return this.find(hql, null, pageOffset, pageSize);
    }

    public Pager<T> find(String hql, Object[] args, int pageOffset, int pageSize) throws Exception {
        Session session2 = sessionFactory.openSession();

        Query query = session2.createQuery(hql);

        //设置参数
        setParameter(query, args);
        Pager<T> pages = new Pager<T>();
        pages.setOffset(pageOffset);
        pages.setSize(pageSize);
        query.setFirstResult(pageOffset).setMaxResults(pageSize);
        List<T> datas = query.list();
        pages.setDatas(datas);
        pages.setTotal(datas.size());
        session2.close();
        return pages;
    }

    private void setParameter(Query query, Object[] args) {
        if (args != null && args.length > 0) {
            int index = 0;
            for (Object arg : args) {
                query.setParameter(index++, arg);
            }
        }
    }

    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
