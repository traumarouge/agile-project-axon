package agileproject.backlogitem.query;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class BacklogItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uuid;
    private String name;

    protected BacklogItem() {

        // no-args constructor required by JPA
    }

    public Long getId() {

        return id;
    }


    public String getUuid() {

        return uuid;
    }


    public void setUuid(String uuid) {

        this.uuid = uuid;
    }


    public String getName() {

        return name;
    }


    public void setName(String name) {

        this.name = name;
    }
}
