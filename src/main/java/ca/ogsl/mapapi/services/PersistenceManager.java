package ca.ogsl.mapapi.services;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {

    private static final EntityManagerFactory mapApiEMFFactory = buildEntityManagerFactory("mapapi");

    public static final ThreadLocal<String> languageContext = new ThreadLocal<String>();

    private static EntityManagerFactory buildEntityManagerFactory(String unitName) {
        return Persistence.createEntityManagerFactory(unitName);
    }

    public static EntityManagerFactory getMapApiEMFFactory() {
        return mapApiEMFFactory;
    }

    public static void setLanguageContext(String lang){
        if (lang == null) {
            lang = "fr";
        }
        PersistenceManager.languageContext.set(lang);
    }

    public static String getLocalizedString(String frenchString, String englishString) {
        String lg = languageContext.get();

        if (lg != null) {
            if (lg.equalsIgnoreCase("fr"))
                return frenchString;
            else if (lg.equalsIgnoreCase("en"))
                return englishString;
        }
        return null;
    }

    public static String getIfNoContextLanguage(String localString) {
        if (languageContext.get() == null)
            return localString;

        return null;
    }

    public static String appendLanguageToProperty(String propertyName) {
        String lg = languageContext.get();

        if (lg != null) {
            lg = StringUtils.capitalize(lg);

            return propertyName.replace("__", lg);
            //return propertyName + lg;
        }
        return propertyName;
    }
}