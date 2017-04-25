package ca.ogsl.mapapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "url")
    private String url;
    @Basic
    @Column(name = "projection")
    private String projection;
    @Basic
    @Column(name = "key")
    private String key;
    @Basic
    @Column(name = "imageryset")
    private String imagerySet;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "format")
    private String format;
    @Basic
    @Column(name = "wmsversion")
    private String wmsVersion;
    @Basic
    @JsonProperty(value = "isTiled")
    @Column(name = "istiled")
    private Boolean isTiled;
    @Basic
    @Column(name = "styles")
    private String styles;
    @Basic
    @Column(name = "wmslayers")
    private String wmsLayers;
    @Basic
    @Column(name = "tilesorigin")
    private String tilesOrigin;
    @Basic
    @JsonProperty(value = "isTimeEnabled")
    @Column(name = "istimeenabled")
    private Boolean isTimeEnabled;


    @OneToMany(mappedBy = "source", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JsonManagedReference
    private Set<UrlParam> urlParams;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProjection() {
        return projection;
    }

    public void setProjection(String projection) {
        this.projection = projection;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImagerySet() {
        return imagerySet;
    }

    public void setImageryset(String imagerySet) {
        this.imagerySet = imagerySet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getWmsVersion() {
        return wmsVersion;
    }

    public void setWmsVersion(String wmsVersion) {
        this.wmsVersion = wmsVersion;
    }

    @JsonProperty(value = "isTiled")
    public Boolean isTiled() {
        return isTiled;
    }

    @JsonProperty(value = "isTiled")
    public void setTiled(Boolean istiled) {
        this.isTiled = istiled;
    }

    public String getStyles() {
        return styles;
    }

    public void setStyles(String styles) {
        this.styles = styles;
    }

    public String getWmsLayers() {
        return wmsLayers;
    }

    public void setWmsLayers(String wmsLayers) {
        this.wmsLayers = wmsLayers;
    }

    public String getTilesOrigin() {
        return tilesOrigin;
    }

    public void setTilesOrigin(String tilesorigin) {
        this.tilesOrigin = tilesorigin;
    }

    @JsonProperty(value = "isTimeEnabled")
    public Boolean isTimeEnabled() {
        return isTimeEnabled;
    }

    @JsonProperty(value = "isTimeEnabled")
    public void setTimeEnabled(Boolean timeEnabled) {
        isTimeEnabled = timeEnabled;
    }

    public Set<UrlParam> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(Set<UrlParam> urlParams) {
        this.urlParams = urlParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Source source = (Source) o;

        if (id != null ? !id.equals(source.id) : source.id != null) return false;
        if (url != null ? !url.equals(source.url) : source.url != null) return false;
        if (projection != null ? !projection.equals(source.projection) : source.projection != null) return false;
        if (key != null ? !key.equals(source.key) : source.key != null) return false;
        if (imagerySet != null ? !imagerySet.equals(source.imagerySet) : source.imagerySet != null) return false;
        if (type != null ? !type.equals(source.type) : source.type != null) return false;
        if (format != null ? !format.equals(source.format) : source.format != null) return false;
        if (wmsVersion != null ? !wmsVersion.equals(source.wmsVersion) : source.wmsVersion != null) return false;
        if (isTiled != null ? !isTiled.equals(source.isTiled) : source.isTiled != null) return false;
        if (styles != null ? !styles.equals(source.styles) : source.styles != null) return false;
        if (wmsLayers != null ? !wmsLayers.equals(source.wmsLayers) : source.wmsLayers != null) return false;
        if (tilesOrigin != null ? !tilesOrigin.equals(source.tilesOrigin) : source.tilesOrigin != null) return false;
        if (isTimeEnabled != null ? !isTimeEnabled.equals(source.isTimeEnabled) : source.isTimeEnabled != null)
            return false;
        return urlParams != null ? urlParams.equals(source.urlParams) : source.urlParams == null;
    }
}
