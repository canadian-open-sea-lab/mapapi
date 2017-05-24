package ca.ogsl.mapapi.dao;

import ca.ogsl.mapapi.errorhandling.AppException;
import ca.ogsl.mapapi.models.LayerInfo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LayerInfoDao {

    public List<LayerInfo> createMultipleLayerInfos(List<LayerInfo> layerInfos) {
        EntityManager em = MapApiEntityManagerFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();
        List<LayerInfo> databaseLayerInfos = new ArrayList<>();
        try {
            et.begin();
            for (LayerInfo layerInfo : layerInfos) {
                databaseLayerInfos.add(em.merge(layerInfo));
            }
            et.commit();
            return databaseLayerInfos;
        } finally {
            em.close();
        }
    }

    public List<LayerInfo> getLayerInfosForLayerIdOrdered(String lang, Integer layerId) throws AppException {
        PersistenceManager.setLanguageContext(lang);
        EntityManager em = MapApiEntityManagerFactory.createEntityManager();
        List<LayerInfo> databaseLayerInfos;
        CriteriaBuilder cb = MapApiEntityManagerFactory.getCriteriaBuilder();
        try {
            CriteriaQuery<LayerInfo> cq = cb.createQuery(LayerInfo.class);
            Root<LayerInfo> root = cq.from(LayerInfo.class);
            cq.where(cb.equal(root.get("layerId"), layerId));
            cq.orderBy(cb.asc(root.get(PersistenceManager.appendLanguageToProperty("label__"))));
            TypedQuery<LayerInfo> tq = em.createQuery(cq);
            databaseLayerInfos = tq.getResultList();
        } finally {
            em.close();
        }
        return databaseLayerInfos;

    }
}
