package ca.ogsl.mapapi.dao;

import ca.ogsl.mapapi.errorhandling.AppException;
import ca.ogsl.mapapi.models.Layer;
import ca.ogsl.mapapi.util.GenericsUtil;
import org.hibernate.transform.DistinctResultTransformer;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@SuppressWarnings("Duplicates")
public class LayerDao {

    public List<Layer> listLayers(String lang) throws AppException {
        PersistenceManager.setLanguageContext(lang);
        EntityManager em = MapApiEntityManagerFactory.createEntityManager();
        CriteriaBuilder cb = MapApiEntityManagerFactory.getCriteriaBuilder();
        List<Layer> layers;
        try {
            CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
            Root<Layer> root = cq.from(Layer.class);
            Join sourceJoin = (Join) root.fetch("source", JoinType.LEFT);
            Join legendsJoin = (Join) root.fetch("legends", JoinType.LEFT);
            TypedQuery<Layer> tq = em.createQuery(cq);
            layers = tq.getResultList();
        } finally {
            em.close();
        }
        return DistinctResultTransformer.INSTANCE.transformList(layers);
    }

    public Layer getLayerForId(String lang, Integer id) throws AppException {
        PersistenceManager.setLanguageContext(lang);
        EntityManager em = MapApiEntityManagerFactory.createEntityManager();
        CriteriaBuilder cb = MapApiEntityManagerFactory.getCriteriaBuilder();
        Layer layer;
        try {
            CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
            Root<Layer> root = cq.from(Layer.class);
            Join sourceJoin = (Join) root.fetch("source", JoinType.LEFT);
            Join legendsJoin = (Join) root.fetch("legends", JoinType.LEFT);
            Join urlParamJoin = (Join) sourceJoin.fetch("urlParams", JoinType.LEFT);
            cq.where(cb.equal(root.get("id"), id));
            TypedQuery<Layer> tq = em.createQuery(cq);
            layer = GenericsUtil.getSingleResultOrNull(tq);
        } finally {
            em.close();
        }
        return layer;
    }

    public Layer getLayerForCode(String lang, String code) throws AppException {
        PersistenceManager.setLanguageContext(lang);
        EntityManager em = MapApiEntityManagerFactory.createEntityManager();
        CriteriaBuilder cb = MapApiEntityManagerFactory.getCriteriaBuilder();
        Layer layer;
        try {
            CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
            Root<Layer> root = cq.from(Layer.class);
            Join sourceJoin = (Join) root.fetch("source", JoinType.LEFT);
            Join legendsJoin = (Join) root.fetch("legends", JoinType.LEFT);
            Join urlParamJoin = (Join) sourceJoin.fetch("urlParams", JoinType.LEFT);
            cq.where(cb.equal(root.get("code"), code));
            TypedQuery<Layer> tq = em.createQuery(cq);
            layer = GenericsUtil.getSingleResultOrNull(tq);
        } finally {
            em.close();
        }
        return layer;
    }
}
