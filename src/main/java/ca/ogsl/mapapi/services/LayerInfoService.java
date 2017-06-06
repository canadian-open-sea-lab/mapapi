package ca.ogsl.mapapi.services;

import ca.ogsl.mapapi.dao.GenericDao;
import ca.ogsl.mapapi.dao.LayerInfoDao;
import ca.ogsl.mapapi.errorhandling.AppException;
import ca.ogsl.mapapi.models.LayerInfo;
import ca.ogsl.mapapi.util.ValidationUtil;

import java.util.List;

public class LayerInfoService {

    private GenericDao genericDao = new GenericDao();
    private LayerInfoDao layerInfoDao = new LayerInfoDao();

    public LayerInfo getLayerInfoForId(String lang, Integer id) {
        return this.genericDao.getEntityFromId(lang, id, LayerInfo.class);
    }

    public LayerInfo postCreateLayerInfo(LayerInfo layerInfo, String role) throws AppException {
        ValidationUtil.validateAdminRole(role);
        return this.genericDao.mergeEntity(layerInfo);
    }

    public List<LayerInfo> postCreateMultipleLayerInfos(List<LayerInfo> layerInfos, String role)
            throws AppException {
        ValidationUtil.validateAdminRole(role);
        return this.layerInfoDao.createMultipleLayerInfos(layerInfos);
    }

    public void deleteLayerInfoForId(Integer id, String role) throws AppException {
        ValidationUtil.validateAdminRole(role);
        this.genericDao.deleteEntityFromId(id, LayerInfo.class);
    }

    public List<LayerInfo> listLayerInfos(String lang) {
        return this.genericDao.getAllEntities(lang, LayerInfo.class);
    }
}
