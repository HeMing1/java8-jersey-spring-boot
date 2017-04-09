package com.thoughtworks.gaia.product.model;

import com.thoughtworks.gaia.common.jpa.IdBaseModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "B")
public class BModel extends IdBaseModel {
    @Column(name = "name", length = 64, nullable = false)
    private String name;
    @Column(name = "time_created", nullable = false, updatable = false)
    private Date time_created;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "a_id")
    private AModel a;

    public AModel getA() {
        return a;
    }

    public void setA(AModel a) {
        this.a = a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime_created() {
        return time_created;
    }

    public void setTime_created(Date time_created) {
        this.time_created = time_created;
    }
}