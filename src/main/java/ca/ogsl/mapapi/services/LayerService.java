package ca.ogsl.mapapi.services;

import ca.ogsl.mapapi.models.Layer;
import ca.ogsl.mapapi.models.LayerDescription;
import ca.ogsl.mapapi.models.LayerInfo;
import ca.ogsl.mapapi.util.GenericsUtil;
import org.hibernate.transform.DistinctResultTransformer;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by desjardisna on 2017-02-28.
 */
@SuppressWarnings("Duplicates")
@Path("layer")
@Produces(MediaType.APPLICATION_JSON)
public class LayerService {

    public LayerService(){}

    @GET
    public Response listLayers(@QueryParam("lang") String lang){
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        List<Layer> layers;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
            Root<Layer> root = cq.from(Layer.class);
            Join sourceJoin = (Join)root.fetch("source", JoinType.LEFT);
            Join legendsJoin = (Join)root.fetch("legends", JoinType.LEFT);
            TypedQuery<Layer> tq = em.createQuery(cq);
            layers = tq.getResultList();
            return Response.status(200).entity(DistinctResultTransformer.INSTANCE.transformList(layers)).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response layers(Layer layer) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(layer);
        et.commit();
        return Response.status(200).entity(layer).build();
    }

    @GET
    @Path("{id}")
    public Response getLayerForId(@QueryParam("lang") String lang, @PathParam("id") Integer id){
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        Layer layer;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
            Root<Layer> root = cq.from(Layer.class);
            Join sourceJoin = (Join)root.fetch("source", JoinType.LEFT);
            Join legendsJoin = (Join)root.fetch("legends", JoinType.LEFT);
            Join urlParamJoin = (Join) sourceJoin.fetch("urlParams", JoinType.LEFT);
            cq.where(cb.equal(root.get("id"), id));
            TypedQuery<Layer> tq = em.createQuery(cq);
            layer = GenericsUtil.getSingleResultOrNull(tq);
            return Response.status(200).entity(layer).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }
    @GET
    @Path("getLayerForCode")
    public Response getlayerForCode(@QueryParam("lang") String lang, @QueryParam("code") String code){
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        Layer layer;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
            Root<Layer> root = cq.from(Layer.class);
            Join sourceJoin = (Join)root.fetch("source", JoinType.LEFT);
            Join legendsJoin = (Join)root.fetch("legends", JoinType.LEFT);
            Join urlParamJoin = (Join) sourceJoin.fetch("urlParams", JoinType.LEFT);
            cq.where(cb.equal(root.get("code"), code));
            TypedQuery<Layer> tq = em.createQuery(cq);
            layer = GenericsUtil.getSingleResultOrNull(tq);
            return Response.status(200).entity(layer).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }

    @GET
    @Path("{id}/getLayerInformation")
    @Produces(MediaType.TEXT_HTML)
    public Response getLayerInformation(@QueryParam("lang") String lang, @PathParam("id") Integer layerId){

        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("mapapi");
        EntityManager em= emf.createEntityManager();
        LayerDescription layerDescription;
        List<LayerInfo> layerInfos;
        try {
            layerDescription = getLayerDescription(layerId, em);
            if (layerDescription==null){
                return Response.status(404).entity("No information found for layer").build();
            }
            layerInfos = getLayerInfos(layerId, em);
            String htmlContent = getLayerInformationHtml(layerDescription, layerInfos);

            return Response.status(200).entity(htmlContent).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }

    private List<LayerInfo> getLayerInfos(Integer layerId, EntityManager em) {
        List<LayerInfo> layerInfos;CriteriaBuilder cb= em.getCriteriaBuilder();
        CriteriaQuery<LayerInfo> cq = cb.createQuery(LayerInfo.class);
        Root<LayerInfo> root =  cq.from(LayerInfo.class);
        cq.where(cb.equal(root.get("layerId"),layerId));
        cq.orderBy(cb.asc(root.get(PersistenceManager.appendLanguageToProperty("label__"))));
        TypedQuery<LayerInfo> tq= em.createQuery(cq);
        layerInfos = tq.getResultList();
        return layerInfos;
    }

    private LayerDescription getLayerDescription(Integer layerId, EntityManager em) {
        LayerDescription layerDescription;CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LayerDescription> cq = cb.createQuery(LayerDescription.class);
        Root<LayerDescription> root =  cq.from(LayerDescription.class);
        cq.where(cb.and(cb.equal(root.get("layerId"),layerId)));
        TypedQuery<LayerDescription> tq = em.createQuery(cq);
        layerDescription = GenericsUtil.getSingleResultOrNull(tq);
        return layerDescription;
    }

    private String getLayerInformationHtml(LayerDescription layerDescription, List<LayerInfo> layerInfos) {
        StringBuilder stringBuilder= new StringBuilder();
        stringBuilder.append("<div class='layerInfo'>");
        stringBuilder.append("<h4 class='layerDescriptionTitle'>");
        stringBuilder.append(layerDescription.getTitle__()).append("</h4>");
        stringBuilder.append("<div class='layerDescription'>");
        stringBuilder.append(layerDescription.getDescription__());
        stringBuilder.append("</div>");
        stringBuilder.append("<div class='layerInformationsContainer'><h5 class='layerInformationsTitle'>Informations</h5>");
        stringBuilder.append("<table class='layerInformations'><tbody>");
        for (LayerInfo layerInfo: layerInfos){
            stringBuilder.append("<tr class='layerInformation'><td class='layerInformationTdLeft'>").append(layerInfo.getLabel__()).append("</td><td class='layerInformationTdRight'>");
            if (layerInfo.getUrl__()!=null && !layerInfo.getUrl__().equals("")){
                stringBuilder.append("<a class='layerInformationLink' target='_blank' href='").append(layerInfo.getUrl__()).append("'>")
                        .append(layerInfo.getValue__()).append("</a></td>");
            }
            else{
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
