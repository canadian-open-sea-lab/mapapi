package ca.ogsl.mapapi.dao;

import ca.ogsl.mapapi.models.LayerInfo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
}
