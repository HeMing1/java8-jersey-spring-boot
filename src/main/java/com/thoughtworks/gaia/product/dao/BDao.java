package com.thoughtworks.gaia.product.dao;

import com.thoughtworks.gaia.common.jpa.BaseDaoWrapper;
import com.thoughtworks.gaia.product.model.BModel;
import org.springframework.stereotype.Component;

@Component
public class BDao extends BaseDaoWrapper<BModel>{
    public BDao(){
        super(BModel.class);
    }
}