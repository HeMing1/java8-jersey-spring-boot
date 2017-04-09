package com.thoughtworks.gaia.product.entity;

import com.eureka2.shading.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Date;

public class B{
    private Long id;
    private String name;
    private Date time_created;
    private A a;

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

    public Date getTime_created() {
        return time_created;
    }

    public void setTime_created(Date time_created) {
        this.time_created = time_created;
    }
    public String toJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }
}