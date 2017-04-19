package ca.ogsl.mapapi.services;

import ca.ogsl.mapapi.filter.AuthenticationFilter;
import ca.ogsl.mapapi.models.LayerDescription;
import ca.ogsl.mapapi.util.GenericsUtil;
import org.hibernate.transform.DistinctResultTransformer;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@SuppressWarnings("Duplicates")
@Path("layerDescription")
@Produces(MediaType.APPLICATION_JSON)
public class LayerDescriptionService {

    public LayerDescriptionService() {
    }

    @GET
    public Response listLayerDescriptions(@QueryParam("lang") String lang) {
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        List<LayerDescription> layerDescriptions;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<LayerDescription> cq = cb.createQuery(LayerDescription.class);
            Root<LayerDescription> root = cq.from(LayerDescription.class);
            TypedQuery<LayerDescription> tq = em.createQuery(cq);
            layerDescriptions = tq.getResultList();
            return Response.status(200).entity(DistinctResultTransformer.INSTANCE.transformList(layerDescriptions)).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }

    @GET
    @Path("{id}")
    public Response getLayerDescriptionForId(@QueryParam("lang") String lang, @PathParam("id") Integer id) {
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        LayerDescription layerDescription;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<LayerDescription> cq = cb.createQuery(LayerDescription.class);
            Root<LayerDescription> root = cq.from(LayerDescription.class);
            cq.where(cb.equal(root.get("id"), id));
            TypedQuery<LayerDescription> tq = em.createQuery(cq);
            layerDescription = GenericsUtil.getSingleResultOrNull(tq);
            return Response.status(200).entity(layerDescription).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteLayerDescriptionForId(@QueryParam("lang") String lang, @PathParam("id") Integer id, @HeaderParam("role") String role) {
        if (role.equals(AuthenticationFilter.ADMIN_ROLE)) {
            PersistenceManager.setLanguageContext(lang);
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
            EntityManager em = emf.createEntityManager();
            LayerDescription layerDescription;
            EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<LayerDescription> cq = cb.createQuery(LayerDescription.class);
                Root<LayerDescription> root = cq.from(LayerDescription.class);
                cq.where(cb.equal(root.get("id"), id));
                TypedQuery<LayerDescription> tq = em.createQuery(cq);
                layerDescription = GenericsUtil.getSingleResultOrNull(tq);
                em.remove(layerDescription);
                et.commit();
                return Response.status(200).entity(layerDescription).build();
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response layerDescriptions(LayerDescription layerDescription, @HeaderParam("role") String role) {
        if (role.equals(AuthenticationFilter.ADMIN_ROLE)) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.merge(layerDescription);
            et.commit();
            return Response.status(200).entity(layerDescription).build();
        }
        else{
            return Response.status(403).build();
        }
    }
}
