package ca.ogsl.mapapi.services;

import ca.ogsl.mapapi.dao.GenericDao;
import ca.ogsl.mapapi.errorhandling.AppException;
import ca.ogsl.mapapi.models.LayerDescription;
import ca.ogsl.mapapi.util.ValidationUtil;

import java.util.List;

public class LayerDescriptionService {

    private GenericDao genericDao = new GenericDao();

    public List<LayerDescription> listLayerDescriptions(String lang) throws AppException {
        return this.genericDao.getAllEntities(lang, LayerDescription.class);
    }

    public LayerDescription getLayerDescriptionForId(String lang, Integer id) throws AppException {
        return this.genericDao.getEntityFromId(lang, id, LayerDescription.class);
    }

    public void deleteLayerDescriptionForId(Integer id, String role) throws AppException {
        ValidationUtil.validateAdminRole(role);
        this.genericDao.deleteEntityFromId(id, LayerDescription.class);
    }

    public LayerDescription postCreateLayerDescription(LayerDescription layerDescription, String
            role) throws
            AppException{
        ValidationUtil.validateAdminRole(role);
        return this.genericDao.mergeEntity(layerDescription);
    }
}
