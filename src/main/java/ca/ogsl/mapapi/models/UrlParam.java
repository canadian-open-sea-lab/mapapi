package ca.ogsl.mapapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UrlParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "value")
    private String value;
    @Column(name = "source_id", insertable = false, updatable = false)
    private Integer sourceId;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private Source source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String defaultvalue) {
        this.value = defaultvalue;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UrlParam urlParam = (UrlParam) o;

        if (id != null ? !id.equals(urlParam.id) : urlParam.id != null) return false;
        if (name != null ? !name.equals(urlParam.name) : urlParam.name != null) return false;
        if (value != null ? !value.equals(urlParam.value) : urlParam.value != null) return false;
        if (sourceId != null ? !sourceId.equals(urlParam.sourceId) : urlParam.sourceId != null) return false;
        return source != null ? source.equals(urlParam.source) : urlParam.source == null;
    }
}
