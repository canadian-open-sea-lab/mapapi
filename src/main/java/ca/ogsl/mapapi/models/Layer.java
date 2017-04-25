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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
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
    @OneToMany(mappedBy = "layer", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JsonManagedReference
    private Set<Legend> legends;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "style_id")
    private Style style;
    @Column(name = "style_id", updatable = false, insertable = false)
    private Integer styleId;

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

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Layer layer = (Layer) o;

        if (id != null ? !id.equals(layer.id) : layer.id != null) return false;
        if (labelFr != null ? !labelFr.equals(layer.labelFr) : layer.labelFr != null) return false;
        if (labelEn != null ? !labelEn.equals(layer.labelEn) : layer.labelEn != null) return false;
        if (code != null ? !code.equals(layer.code) : layer.code != null) return false;
        if (type != null ? !type.equals(layer.type) : layer.type != null) return false;
        if (zIndex != null ? !zIndex.equals(layer.zIndex) : layer.zIndex != null) return false;
        if (opacity != null ? !opacity.equals(layer.opacity) : layer.opacity != null) return false;
        if (isVisible != null ? !isVisible.equals(layer.isVisible) : layer.isVisible != null) return false;
        if (source != null ? !source.equals(layer.source) : layer.source != null) return false;
        if (isBackground != null ? !isBackground.equals(layer.isBackground) : layer.isBackground != null) return false;
        if (isTimeEnabled != null ? !isTimeEnabled.equals(layer.isTimeEnabled) : layer.isTimeEnabled != null)
            return false;
        if (topics != null ? !topics.equals(layer.topics) : layer.topics != null) return false;
        if (legends != null ? !legends.equals(layer.legends) : layer.legends != null) return false;
        if (style != null ? !style.equals(layer.style) : layer.style != null) return false;
        return styleId != null ? styleId.equals(layer.styleId) : layer.styleId == null;
    }
}
