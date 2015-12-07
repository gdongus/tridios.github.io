package tridios.gd.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
public class BaseModel {
    public BaseModel() {
    }

    @Id
    @GeneratedValue
    private Long id;

    @JsonManagedReference
    @OneToOne(mappedBy = "baseModel", cascade = CascadeType.ALL)
    private Foo foo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Foo getFoo() {
        return foo;
    }

    public void setFoo(Foo foo) {
        this.foo = foo;
    }
}
