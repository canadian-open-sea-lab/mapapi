package ca.ogsl.mapapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;


import javax.persistence.*;

@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "root")
    private Category root;
    @Basic
    @Column(name = "code")
    private String code;

    public Integer getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        if (id != null ? !id.equals(topic.id) : topic.id != null) return false;
        if (root != null ? !root.equals(topic.root) : topic.root != null) return false;
        return code != null ? code.equals(topic.code) : topic.code == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (root != null ? root.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    public Category getRoot() {
        return root;
    }

    public void setRoot(Category root) {
        this.root = root;
    }
}
