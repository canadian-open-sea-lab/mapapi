package ca.ogsl.mapapi.services;

import ca.ogsl.mapapi.dao.PersistenceManager;
import ca.ogsl.mapapi.models.Style;
import ca.ogsl.mapapi.util.GenericsUtil;
import org.hibernate.transform.DistinctResultTransformer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@SuppressWarnings("Duplicates")
@Path("style")
@Produces(MediaType.APPLICATION_JSON)
public class StyleService {

    public StyleService() {
    }

    @GET
    public Response listStyles(@QueryParam("lang") String lang) {
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        List<Style> styles;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Style> cq = cb.createQuery(Style.class);
            Root<Style> root = cq.from(Style.class);
            TypedQuery<Style> tq = em.createQuery(cq);
            styles = tq.getResultList();
            return Response.status(200).entity(DistinctResultTransformer.INSTANCE.transformList(styles)).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }

    @GET
    @Path("{id}")
    public Response getStyleForId(@QueryParam("lang") String lang, @PathParam("id") Integer id) {
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        Style style;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Style> cq = cb.createQuery(Style.class);
            Root<Style> root = cq.from(Style.class);
            cq.where(cb.equal(root.get("id"), id));
            TypedQuery<Style> tq = em.createQuery(cq);
            style = GenericsUtil.getSingleResultOrNull(tq);
            return Response.status(200).entity(style).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }


}
