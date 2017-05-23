package ca.ogsl.mapapi.dao;

import ca.ogsl.mapapi.util.GenericsUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class GenericDao {
    public <T> T getEntityFromCode(String lang, String code, Class clazz) {
        PersistenceManager.setLanguageContext(lang);
        T entity;
        EntityManager em = MapApiEntityManagerFactory.createEntityManager();
        CriteriaBuilder cb = MapApiEntityManagerFactory.getCriteriaBuilder();
        try {
            CriteriaQuery<T> cq = cb.createQuery(clazz);
            Root<T> root = cq.from(clazz);
            cq.where(cb.equal(root.get("code"), code));
            TypedQuery<T> tq = em.createQuery(cq);
            entity = GenericsUtil.getSingleResultOrNull(tq);
            return entity;
        } finally {
            em.close();
        }
    }

    public <T> T getEntityFromId(String lang, Integer id, Class clazz) {
        PersistenceManager.setLanguageContext(lang);
        T entity;
        EntityManager em = MapApiEntityManagerFactory.createEntityManager();
        CriteriaBuilder cb = MapApiEntityManagerFactory.getCriteriaBuilder();
        try {
            CriteriaQuery<T> cq = cb.createQuery(clazz);
            Root<T> root = cq.from(clazz);
            cq.where(cb.equal(root.get("id"), id));
            TypedQuery<T> tq = em.createQuery(cq);
            entity = GenericsUtil.getSingleResultOrNull(tq);
        } finally {
            em.close();
        }
        return entity;
    }

    public <T> T mergeEntity(T entity) {
        EntityManager em = MapApiEntityManagerFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();
        T databaseEntity;
        try {
            et.begin();
            databaseEntity = em.merge(entity);
            et.commit();
        } finally {
            em.close();
        }
        return databaseEntity;
    }

    public <T> void deleteEntityFromId(Integer id, Class clazz) {
        EntityManager em = MapApiEntityManagerFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();
        T databaseEntity;
        try {
            et.begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(clazz);
            Root<T> root = cq.from(clazz);
            cq.where(cb.equal(root.get("id"), id));
            TypedQuery<T> tq = em.createQuery(cq);
            databaseEntity = GenericsUtil.getSingleResultOrNull(tq);
            em.remove(databaseEntity);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
