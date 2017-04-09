package com.thoughtworks.gaia.product.service;

import com.thoughtworks.gaia.common.Loggable;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.product.ProductMapper;
import com.thoughtworks.gaia.product.dao.BDao;
import com.thoughtworks.gaia.product.entity.A;
import com.thoughtworks.gaia.product.entity.B;
import com.thoughtworks.gaia.product.model.AModel;
import com.thoughtworks.gaia.product.model.BModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;

@Component
@Transactional
public class BService implements Loggable{
    @Autowired
    private BDao bDao;
    @Autowired
    private ProductMapper mapper;
    public B getB(Long b_id){
        BModel bModel = bDao.idEquals(b_id).querySingle();
        if (bModel==null) {
            error("can't find resource by id");
            throw new NotFoundException();
        }
        B b = mapper.map(bModel,B.class);
        b.setA(mapper.map(bModel.getA(),A.class));
        return b;
    }

    public B addB(B b){
        BModel bModel = mapper.map(b,BModel.class);
        bModel.setTime_created(new Date());
        bModel.setA(mapper.map(b.getA(), AModel.class));
        bModel.getA().setTime_created(new Date());
        bDao.save(bModel);
        B addedB = mapper.map(bModel,B.class);
        addedB.setA(mapper.map(bModel.getA(),A.class));
        return addedB;
    }

    public B updateB(B b){
        BModel bModel = mapper.map(b,BModel.class);
        bModel.setA(mapper.map(b.getA(), AModel.class));
        bModel.getA().setTime_created(new Date());
        BModel updBModel = bDao.update(bModel);
        B updB = mapper.map(updBModel,B.class);
        updB.setA(mapper.map(updBModel.getA(),A.class));
        return updB;
    }

    public B deleteB(Long b_id){
        BModel bModel = bDao.idEquals(b_id).querySingle();
        bDao.remove(bModel);
        B delB = mapper.map(bModel,B.class);
        delB.setA(mapper.map(bModel.getA(),A.class));
        return delB;
    }
}