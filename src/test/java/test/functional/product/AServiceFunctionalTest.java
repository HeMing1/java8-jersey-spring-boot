package test.functional.product;

import com.thoughtworks.gaia.GaiaApplication;
import com.thoughtworks.gaia.common.constant.EnvProfile;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.product.ProductMapper;
import com.thoughtworks.gaia.product.dao.ADao;
import com.thoughtworks.gaia.product.dao.ProductDao;
import com.thoughtworks.gaia.product.entity.A;
import com.thoughtworks.gaia.product.entity.Product;
import com.thoughtworks.gaia.product.model.AModel;
import com.thoughtworks.gaia.product.model.ProductModel;
import com.thoughtworks.gaia.product.service.AService;
import com.thoughtworks.gaia.product.service.ProductService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(GaiaApplication.class)
@Rollback
@Transactional
@ActiveProfiles({EnvProfile.TEST})
public class AServiceFunctionalTest {
    @Autowired
    private AService aService;

    @Autowired
    private ADao aDao;

    @Autowired
    private ProductMapper mapper;

    @Test
    public void should_get_a_with_id() throws Exception {
        AModel aModel = getAModel();
        aDao.save(aModel);

        A a = aService.getA(aModel.getId());
        assertThat(a.getId()).isEqualTo(aModel.getId());
    }

    public AModel getAModel(){
        AModel aModel = new AModel();
        aModel.setName("name");
        aModel.setTime_created(new Date());
        return aModel;
    }
//    @Test
//    public void test_addA() throws Exception {
//        AModel aModel = getAModel();
//        A a = mapper.map(aModel,A.class);
//        A addedA = aService.addA(a);
//        assertThat(addedA.getId()).isEqualTo(1L);
//    }

    @Test
    public void test_deleteA() throws Exception {
        AModel aModel = getAModel();
        aDao.save(aModel);
        aService.deleteA(aModel.getId());
        AModel aModel1 = aDao.idEquals(aModel.getId()).querySingle();
        assertThat(aModel1).isEqualTo(null);
    }

    @Test
    public void test_updateA() throws Exception {
        AModel aModel = getAModel();
        aDao.save(aModel);
        A a = new A();
        a.setName("lalala");
        a.setId(aModel.getId());
        A newA = aService.updateA(a);
        assertThat(aDao.idEquals(aModel.getId()).querySingle().getName()).isEqualTo("lalala");
    }
}
