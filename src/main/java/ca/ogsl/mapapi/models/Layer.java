package ca.ogsl.mapapi.models;

import ca.ogsl.mapapi.services.PersistenceManager;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Layer {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "labelfr", nullable = false)
    private String labelFr;
    @Basic
    @Column(name = "labelen", nullable = false)
    private String labelEn;
    @Basic
    @Column(name = "code")
    private String code;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "zindex")
    private Integer zIndex;
    @Basic
    @Column(name = "opacity")
    private Double opacity;
    @Basic
    @JsonProperty(value = "isVisible")
    @Column(name = "isvisible")
    private Boolean isVisible;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private Source source;
    @Basic
    @JsonProperty(value = "isBackground")
    @Column(name = "isbackground")
    private Boolean isBackground;
    @Basic
    @JsonProperty(value = "isTimeEnabled")
    @Column(name = "istimeenabled")
    private Boolean isTimeEnabled;
    @JoinTable(name = "layer_topic", joinColumns = {
            @JoinColumn(name = "layer_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "topic_id", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    private Collection<Topic> topics;
    @OneToMany(mappedBy = "layer", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Legend> legends;
    @Basic
    @Column(name = "styleurl")
    private String styleUrl;

    public Integer getId() {
        return id;
    }

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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getzIndex() {
        return zIndex;
    }

    public void setzIndex(Integer zIndex) {
        this.zIndex = zIndex;
    }

    public Double getOpacity() {
        return opacity;
    }

    public void setOpacity(Double opacity) {
        this.opacity = opacity;
    }

    @JsonProperty(value = "isVisible")
    public Boolean isVisible() {
        return isVisible;
    }

    @JsonProperty(value = "isVisible")
    public void setVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @JsonProperty(value = "isBackground")
    public Boolean isBackground() {
        return isBackground;
    }

    @JsonProperty(value = "isBackground")
    public void setBackground(Boolean background) {
        isBackground = background;
    }

    @JsonProperty(value = "isTimeEnabled")
    public Boolean isTimeEnabled() {
        return isTimeEnabled;
    }

    @JsonProperty(value = "isTimeEnabled")
    public void setTimeEnabled(Boolean timeEnabled) {
        isTimeEnabled = timeEnabled;
    }


    public Collection<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Collection<Topic> topics) {
        this.topics = topics;
    }

    public Set<Legend> getLegends() {
        return legends;
    }

    public void setLegends(Set<Legend> legends) {
        this.legends = legends;
    }

    public String getStyleUrl() {
        return styleUrl;
    }

    public void setStyleUrl(String styleUrl) {
        this.styleUrl = styleUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Layer layer = (Layer) o;

        if (id != null ? !id.equals(layer.id) : layer.id != null) return false;
        if (code != null ? !code.equals(layer.code) : layer.code != null) return false;
        if (type != null ? !type.equals(layer.type) : layer.type != null) return false;
        if (zIndex != null ? !zIndex.equals(layer.zIndex) : layer.zIndex != null) return false;
        if (opacity != null ? !opacity.equals(layer.opacity) : layer.opacity != null) return false;
        if (isVisible != null ? !isVisible.equals(layer.isVisible) : layer.isVisible != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (zIndex != null ? zIndex.hashCode() : 0);
        result = 31 * result + (opacity != null ? opacity.hashCode() : 0);
        result = 31 * result + (isVisible != null ? isVisible.hashCode() : 0);
        return result;
    }
}
