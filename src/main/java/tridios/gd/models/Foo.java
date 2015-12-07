package tridios.gd.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Foo {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.PERSIST)
    private BaseModel baseModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseModel getBaseModel() {
        return baseModel;
    }

    public void setBaseModel(BaseModel baseModel) {
        this.baseModel = baseModel;
    }
}
