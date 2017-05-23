package ca.ogsl.mapapi.models;

import ca.ogsl.mapapi.dao.PersistenceManager;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Legend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "urlfr")
    private String urlFr;
    @Basic
    @Column(name = "urlen")
    private String urlEn;
    @Column(name = "layer_id", insertable = false, updatable = false)
    private Integer layerId;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "layer_id")
    private Layer layer;
    @Basic
    @Column(name = "labelfr", nullable = false)
    private String labelFr;
    @Basic
    @Column(name = "labelen", nullable = false)
    private String labelEn;
    @Basic
    @Column(name = "isdefaultlegend")
    @JsonProperty(value = "isDefaultLegend")
    private Boolean isDefaultLegend;

    public String getLabel__() {
        return PersistenceManager.getLocalizedString(this.labelFr, this.labelEn);
    }

    public String getLabelFr() {
        return PersistenceManager.getIfNoContextLanguage(this.labelFr);
    }

    public void setLabelFr(String labelFr) {
        this.labelFr = labelFr;
    }

    public String getLabelEn() {
        return PersistenceManager.getIfNoContextLanguage(this.labelEn);
    }

    public void setLabelEn(String labelEn) {
        this.labelEn = labelEn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlFr() {
        return PersistenceManager.getIfNoContextLanguage(this.urlFr);
    }

    public void setUrlFr(String urlfr) {
        this.urlFr = urlfr;
    }

    public String getUrlEn() {
        return PersistenceManager.getIfNoContextLanguage(this.urlEn);
    }

    public void setUrlEn(String urlen) {
        this.urlEn = urlen;
    }

    public String getUrl__() {
        return PersistenceManager.getLocalizedString(this.urlFr, this.urlEn);
    }

    public Integer getLayerId() {
        return layerId;
    }

    public void setLayerId(Integer layerId) {
        this.layerId = layerId;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    @JsonProperty(value = "isDefaultLegend")
    public Boolean isDefaultLegend() {
        return isDefaultLegend;
    }

    @JsonProperty(value = "isDefaultLegend")
    public void setDefaultLegend(Boolean defaultLegend) {
        isDefaultLegend = defaultLegend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Legend legend = (Legend) o;

        if (id != null ? !id.equals(legend.id) : legend.id != null) return false;
        if (urlFr != null ? !urlFr.equals(legend.urlFr) : legend.urlFr != null) return false;
        if (urlEn != null ? !urlEn.equals(legend.urlEn) : legend.urlEn != null) return false;
        if (layerId != null ? !layerId.equals(legend.layerId) : legend.layerId != null) return false;
        if (layer != null ? !layer.equals(legend.layer) : legend.layer != null) return false;
        if (labelFr != null ? !labelFr.equals(legend.labelFr) : legend.labelFr != null) return false;
        if (labelEn != null ? !labelEn.equals(legend.labelEn) : legend.labelEn != null) return false;
        return isDefaultLegend != null ? isDefaultLegend.equals(legend.isDefaultLegend) : legend.isDefaultLegend == null;
    }
}
