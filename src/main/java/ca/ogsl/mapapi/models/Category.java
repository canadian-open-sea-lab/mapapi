package ca.ogsl.mapapi.models;

import ca.ogsl.mapapi.services.PersistenceManager;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Collection;

@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "labelfr", nullable = false)
    private String labelFr;
    @Basic
    @Column(name = "labelen", nullable = false)
    private String labelEn;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "isexpanded")
    @JsonProperty(value = "isExpanded")
    private Boolean isExpanded;
    @Basic
    @Column(name = "layer_id")
    private Integer layerId;
    @JoinTable(name = "category_child", joinColumns = {
            @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "child_id", referencedColumnName = "id", nullable = false)})
    @OrderBy("type ASC")
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Collection<Category> categories;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty(value = "isExpanded")
    public Boolean isExpanded() {
        return isExpanded;
    }

    @JsonProperty(value = "isExpanded")
    public Boolean getExpanded() {
        return isExpanded;
    }

    @JsonProperty(value = "isExpanded")
    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }

    public Integer getLayerId() {
        return layerId;
    }

    public void setLayerId(Integer layerCode) {
        this.layerId = layerCode;
    }

    public Collection<Category> getCategories() {
        return categories;
    }

    public void setCategories(Collection<Category> children) {
        this.categories = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != null ? !id.equals(category.id) : category.id != null) return false;
        if (labelFr != null ? !labelFr.equals(category.labelFr) : category.labelFr != null) return false;
        if (labelEn != null ? !labelEn.equals(category.labelEn) : category.labelEn != null) return false;
        if (type != null ? !type.equals(category.type) : category.type != null) return false;
        if (isExpanded != null ? !isExpanded.equals(category.isExpanded) : category.isExpanded != null) return false;
        if (layerId != null ? !layerId.equals(category.layerId) : category.layerId != null) return false;
        return categories != null ? categories.equals(category.categories) : category.categories == null;
    }
}
