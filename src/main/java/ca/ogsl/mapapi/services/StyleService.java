package ca.ogsl.mapapi.services;

import ca.ogsl.mapapi.dao.GenericDao;
import ca.ogsl.mapapi.errorhandling.AppException;
import ca.ogsl.mapapi.models.Style;

import java.util.List;

public class StyleService {

    private GenericDao genericDao =  new GenericDao();

    public List<Style> listStyles(String lang) throws AppException {
        return this.genericDao.getAllEntities(lang,Style.class);
    }

    public Style getStyleForId(String lang,Integer id) throws AppException{
        return this.genericDao.getEntityFromId(lang, id, Style.class);
    }
}
