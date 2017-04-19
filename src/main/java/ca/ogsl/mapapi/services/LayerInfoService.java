package ca.ogsl.mapapi.services;

import ca.ogsl.mapapi.filter.AuthenticationFilter;
import ca.ogsl.mapapi.models.LayerInfo;
import ca.ogsl.mapapi.util.GenericsUtil;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@SuppressWarnings("Duplicates")
@Path("layerInfo")
@Produces(MediaType.APPLICATION_JSON)
public class LayerInfoService {

    public LayerInfoService() {
    }

    @GET
    @Path("{id}")
    public Response getLayerInfoForId(@QueryParam("lang") String lang, @PathParam("id") Integer id) {
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        LayerInfo layerInfo;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<LayerInfo> cq = cb.createQuery(LayerInfo.class);
            Root<LayerInfo> root = cq.from(LayerInfo.class);
            cq.where(cb.equal(root.get("id"), id));
            TypedQuery<LayerInfo> tq = em.createQuery(cq);
            layerInfo = GenericsUtil.getSingleResultOrNull(tq);
            return Response.status(200).entity(layerInfo).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response layerInfos(LayerInfo layerInfo, @HeaderParam("role") String role) {
        if (role.equals(AuthenticationFilter.ADMIN_ROLE)) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.merge(layerInfo);
            et.commit();
            return Response.status(200).entity(layerInfo).build();
        }
        else{
            return Response.status(403).build();
        }
    }

    @POST
    @Path("postMultipleLayerInfos")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postMultipleLayerInfos(List<LayerInfo> layerInfos, @HeaderParam("role") String role) {
        if (role.equals(AuthenticationFilter.ADMIN_ROLE)) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            for (LayerInfo layerInfo : layerInfos) {
                em.merge(layerInfo);
            }
            et.commit();
            return Response.status(200).entity(layerInfos).build();
        }
        else{
            return Response.status(403).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteLayerInfoForId(@QueryParam("lang") String lang, @PathParam("id") Integer id, @HeaderParam("role") String role) {
        if (role.equals(AuthenticationFilter.ADMIN_ROLE)) {
            PersistenceManager.setLanguageContext(lang);
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
            EntityManager em = emf.createEntityManager();
            LayerInfo layerInfo;
            EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<LayerInfo> cq = cb.createQuery(LayerInfo.class);
                Root<LayerInfo> root = cq.from(LayerInfo.class);
                cq.where(cb.equal(root.get("id"), id));
                TypedQuery<LayerInfo> tq = em.createQuery(cq);
                layerInfo = GenericsUtil.getSingleResultOrNull(tq);
                em.remove(layerInfo);
                et.commit();
                return Response.status(200).entity(layerInfo).build();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
            return Response.status(500).build();
        }
        else{
            return Response.status(403).build();
        }
    }
}
