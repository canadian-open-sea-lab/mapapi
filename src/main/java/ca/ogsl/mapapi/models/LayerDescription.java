package ca.ogsl.mapapi.models;

import ca.ogsl.mapapi.services.PersistenceManager;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by desjardisna on 2017-02-27.
 */
@Entity
public class LayerDescription {
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "descriptionFr")
    private String descriptionFr;
    @Basic
    @Column(name = "descriptionEn")
    private String descriptionEn;
    @Column(name ="layer_id")
    private Integer layerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescriptionFr() {
        return PersistenceManager.getIfNoContextLanguage(this.descriptionFr);
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public String getDescriptionEn() {
        return PersistenceManager.getIfNoContextLanguage(this.descriptionEn);
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescription__(){
        return PersistenceManager.getLocalizedString(this.descriptionFr, this.descriptionEn);
    }

    public Integer getLayerId() {
        return layerId;
    }

    public void setLayerId(Integer layerId) {
        this.layerId = layerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LayerDescription that = (LayerDescription) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (descriptionFr != null ? !descriptionFr.equals(that.descriptionFr) : that.descriptionFr != null)
            return false;
        if (descriptionEn != null ? !descriptionEn.equals(that.descriptionEn) : that.descriptionEn != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (descriptionFr != null ? descriptionFr.hashCode() : 0);
        result = 31 * result + (descriptionEn != null ? descriptionEn.hashCode() : 0);
        return result;
    }
}
