package ca.ogsl.mapapi.models;

import ca.ogsl.mapapi.services.PersistenceManager;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LayerDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "descriptionfr")
    private String descriptionFr;
    @Basic
    @Column(name = "descriptionen")
    private String descriptionEn;
    @Basic
    @Column(name = "titlefr")
    private String titleFr;
    @Basic
    @Column(name = "titleen")
    private String titleEn;
    @Column(name = "layer_id")
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

    public String getTitleFr() {
        return PersistenceManager.getIfNoContextLanguage(this.titleFr);
    }

    public void setTitleFr(String titleFr) {
        this.titleFr = titleFr;
    }

    public String getTitleEn() {
        return PersistenceManager.getIfNoContextLanguage(this.titleEn);
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitle__() {
        return PersistenceManager.getLocalizedString(this.titleFr, this.titleEn);
    }

    public String getDescription__() {
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
        if (titleFr != null ? !titleFr.equals(that.titleFr) : that.titleFr != null) return false;
        if (titleEn != null ? !titleEn.equals(that.titleEn) : that.titleEn != null) return false;
        return layerId != null ? layerId.equals(that.layerId) : that.layerId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (descriptionFr != null ? descriptionFr.hashCode() : 0);
        result = 31 * result + (descriptionEn != null ? descriptionEn.hashCode() : 0);
        result = 31 * result + (titleFr != null ? titleFr.hashCode() : 0);
        result = 31 * result + (titleEn != null ? titleEn.hashCode() : 0);
        result = 31 * result + (layerId != null ? layerId.hashCode() : 0);
        return result;
    }
}
