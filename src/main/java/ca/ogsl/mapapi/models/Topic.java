package ca.ogsl.mapapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;


import javax.persistence.*;

/**
 * Created by desjardisna on 2017-02-13.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Topic {
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch=FetchType.LAZY)
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

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }

    public Category getRoot() {
        return root;
    }

    public void setRoot(Category root) {
        this.root = root;
    }
}
