package be.vdab.testcontainers.domain;

import javax.persistence.*;

@Entity
@Table(name = "personen")
public class Persoon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String voornaam;

    //todo: default constr?
    protected Persoon() {
    }

    public long getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }
}
