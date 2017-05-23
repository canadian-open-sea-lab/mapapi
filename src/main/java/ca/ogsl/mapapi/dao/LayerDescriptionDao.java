package ca.ogsl.mapapi.dao;

import ca.ogsl.mapapi.errorhandling.AppException;
import ca.ogsl.mapapi.models.LayerDescription;
import ca.ogsl.mapapi.util.GenericsUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class LayerDescriptionDao {

    public LayerDescription getLayerDescriptionForLayerId(String lang, Integer layerId) throws AppException {
        LayerDescription layerDescription;
        PersistenceManager.setLanguageContext(lang);
        EntityManager em = MapApiEntityManagerFactory.createEntityManager();
        CriteriaBuilder cb = MapApiEntityManagerFactory.getCriteriaBuilder();
        try {
            CriteriaQuery<LayerDescription> cq = cb.createQuery(LayerDescription.class);
            Root<LayerDescription> root = cq.from(LayerDescription.class);
            cq.where(cb.and(cb.equal(root.get("layerId"), layerId)));
            TypedQuery<LayerDescription> tq = em.createQuery(cq);
            layerDescription = GenericsUtil.getSingleResultOrNull(tq);
        } finally {
            em.close();
        }
        return layerDescription;
    }
}
