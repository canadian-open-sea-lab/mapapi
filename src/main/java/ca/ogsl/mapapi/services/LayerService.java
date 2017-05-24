package ca.ogsl.mapapi.services;

import ca.ogsl.mapapi.dao.GenericDao;
import ca.ogsl.mapapi.dao.LayerDao;
import ca.ogsl.mapapi.dao.LayerDescriptionDao;
import ca.ogsl.mapapi.dao.LayerInfoDao;
import ca.ogsl.mapapi.errorhandling.AppException;
import ca.ogsl.mapapi.models.Layer;
import ca.ogsl.mapapi.models.LayerDescription;
import ca.ogsl.mapapi.models.LayerInfo;
import ca.ogsl.mapapi.util.AppConstants;
import ca.ogsl.mapapi.util.ValidationUtil;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by desjardisna on 2017-02-28.
 */
public class LayerService {

    private GenericDao genericDao = new GenericDao();
    private LayerDao layerDao = new LayerDao();

    public List<Layer> listLayers(String lang) throws AppException {
        return this.layerDao.listLayers(lang);
    }

    public Layer postCreateLayer(Layer layer, String role) throws AppException {
        ValidationUtil.validateAdminRole(role);
        return this.genericDao.mergeEntity(layer);
    }

    public Layer getLayerForId(String lang, Integer id) throws AppException {
        return this.layerDao.getLayerForId(lang, id);
    }

    public Layer getlayerForCode(String lang, String code) throws AppException {
        return this.layerDao.getLayerForCode(lang, code);
    }

    public String getLayerInformation(String lang, Integer layerId) throws AppException {
        LayerDescription layerDescription;
        LayerDescriptionDao ldd = new LayerDescriptionDao();
        LayerInfoDao lid = new LayerInfoDao();
        List<LayerInfo> layerInfos;
        layerDescription = ldd.getLayerDescriptionForLayerId(lang, layerId);
        if (layerDescription == null) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 404,
                    "No layer information for given layer", AppConstants.PORTAL_URL);
        }
        layerInfos = lid.getLayerInfosForLayerIdOrdered(lang, layerId);
        return this.buildLayerInformationHtml(layerDescription, layerInfos);
    }

    private String buildLayerInformationHtml(LayerDescription layerDescription, List<LayerInfo> layerInfos) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<div class='layerInfo'>");
        stringBuilder.append("<h4 class='layerDescriptionTitle'>");
        stringBuilder.append(layerDescription.getTitle__()).append("</h4>");
        stringBuilder.append("<div class='layerDescription'>");
        stringBuilder.append(layerDescription.getDescription__());
        stringBuilder.append("</div>");
        stringBuilder.append("<div class='layerInformationsContainer'><h5 class='layerInformationsTitle'>Informations</h5>");
        stringBuilder.append("<table class='layerInformations'><tbody>");
        for (LayerInfo layerInfo : layerInfos) {
            stringBuilder.append("<tr class='layerInformation'><td class='layerInformationTdLeft'>").append(layerInfo.getLabel__()).append("</td><td class='layerInformationTdRight'>");
            if (layerInfo.getUrl__() != null && !layerInfo.getUrl__().equals("")) {
                stringBuilder.append("<a class='layerInformationLink' target='_blank' href='").append(layerInfo.getUrl__()).append("'>")
                        .append(layerInfo.getValue__()).append("</a></td>");
            } else {
                stringBuilder.append(layerInfo.getValue__()).append("</td>");
            }
            stringBuilder.append("</tr>");
        }
        stringBuilder.append("</tbody></table>");
        stringBuilder.append("</div>");
        stringBuilder.append("</div>");
        return stringBuilder.toString();
    }
}
