package tridios.web.models;

import javax.persistence.*;

@Entity
public class BaseModel {
    public BaseModel() {
    }

    @Id
    @GeneratedValue
    private Long id;

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
