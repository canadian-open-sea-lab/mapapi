package ca.ogsl.mapapi.util;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by desjardisna on 2017-02-28.
 */
public class GenericsUtil {
    /**
     * Obtient un résultat unique ou null si aucun résultat n'est présent pour la présente TypedQuery
     *
     * @param query la requête à évaluer
     * @param <T>   Type de la requête
     * @return le résultat unique ou null si aucun
     */
    public static <T> T getSingleResultOrNull(TypedQuery<T> query) {
        query.setMaxResults(1);
        List<T> list = query.getResultList();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
