package ca.ogsl.mapapi.dao;

import ca.ogsl.mapapi.models.Layer;
import ca.ogsl.mapapi.models.Topic;
import ca.ogsl.mapapi.util.GenericsUtil;
import org.hibernate.transform.DistinctResultTransformer;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class TopicDao {

    public Topic getTopicForCode(String lang, String code) throws Exception {
        PersistenceManager.setLanguageContext(lang);
        EntityManager em = MapApiEntityManagerFactory.createEntityManager();
        CriteriaBuilder cb = MapApiEntityManagerFactory.getCriteriaBuilder();
        Topic topic;
        try {
            CriteriaQuery<Topic> cq = cb.createQuery(Topic.class);
            Root<Topic> root = cq.from(Topic.class);
            Join categoryJoin = (Join) root.fetch("root", JoinType.LEFT);
            cq.where(cb.equal(root.get("code"), code));
            TypedQuery<Topic> tq = em.createQuery(cq);
            topic = GenericsUtil.getSingleResultOrNull(tq);
            GenericsUtil gu = new GenericsUtil();
            gu.recursiveInitialize(topic, Topic.class);
        } finally {
            em.close();
        }
        return topic;
    }

    public List<Layer> getBaseLayersForTopicCode(String lang, String code) {
        PersistenceManager.setLanguageContext(lang);
        EntityManager em = MapApiEntityManagerFactory.createEntityManager();
        CriteriaBuilder cb = MapApiEntityManagerFactory.getCriteriaBuilder();
        List<Layer> layers;
        try {
            CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
            Root<Layer> root = cq.from(Layer.class);
            Join topicJoin = root.join("topics", JoinType.LEFT);
            Join sourceJoin = (Join) root.fetch("source", JoinType.LEFT);
            Join urlParamJoin = (Join) sourceJoin.fetch("urlParams", JoinType.LEFT);
            cq.where(cb.and(cb.equal(topicJoin.get("code"), code), cb.equal(root.get("isBackground"), true)));
            TypedQuery<Layer> tq = em.createQuery(cq);
            layers = tq.getResultList();
        } finally {
            em.close();
        }
        return DistinctResultTransformer.INSTANCE.transformList(layers);
    }

    public List<Layer> getLayersForTopicCode(String lang, String code) {
        PersistenceManager.setLanguageContext(lang);
        EntityManager em = MapApiEntityManagerFactory.createEntityManager();
        CriteriaBuilder cb = MapApiEntityManagerFactory.getCriteriaBuilder();
        List<Layer> layers;
        try {
            CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
            Root<Layer> root = cq.from(Layer.class);
            Join topicJoin = root.join("topics", JoinType.LEFT);
            Join legendsJoin = (Join) root.fetch("legends", JoinType.LEFT);
            Join sourceJoin = (Join) root.fetch("source", JoinType.LEFT);
            Join urlParamJoin = (Join) sourceJoin.fetch("urlParams", JoinType.LEFT);
            cq.where(cb.and(cb.equal(topicJoin.get("code"), code), cb.equal(root.get("isBackground"), false)));
            TypedQuery<Layer> tq = em.createQuery(cq);
            layers = tq.getResultList();
        } finally {
            em.close();
        }
        return DistinctResultTransformer.INSTANCE.transformList(layers);
    }
}
