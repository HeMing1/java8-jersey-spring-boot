package test.functional.product;

import com.netflix.discovery.converters.Auto;
import com.thoughtworks.gaia.GaiaApplication;
import com.thoughtworks.gaia.common.constant.EnvProfile;
import com.thoughtworks.gaia.product.ProductMapper;
import com.thoughtworks.gaia.product.dao.ADao;
import com.thoughtworks.gaia.product.dao.BDao;
import com.thoughtworks.gaia.product.entity.A;
import com.thoughtworks.gaia.product.entity.B;
import com.thoughtworks.gaia.product.model.AModel;
import com.thoughtworks.gaia.product.model.BModel;
import com.thoughtworks.gaia.product.service.AService;
import com.thoughtworks.gaia.product.service.BService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(GaiaApplication.class)
@Rollback
@Transactional
@ActiveProfiles({EnvProfile.TEST})
public class BServiceFunctionalTest {
    @Autowired
    private BDao bDao;
    @Autowired
    private ProductMapper mapper;
    @Autowired
    private BService bService;
    @Autowired
    private AService aService;
    @Autowired
    private ADao aDao;

    public BModel newBModel() {
        BModel bModel = new BModel();
        bModel.setName("b_name");
        bModel.setTime_created(new Date());
        bModel.setA(newAModel());
        return bModel;
    }

    public AModel newAModel() {
        AModel aModel = new AModel();
        aModel.setName("a_name");
        aModel.setTime_created(new Date());
        return aModel;
    }

    @Test
    public void test_get_function() throws Exception {
        BModel bModel = newBModel();
        bDao.save(bModel);
        B b = bService.getB(bModel.getId());
        assertThat(bModel.toJson()).isEqualTo(b.toJson());
    }

    @Test
    public void test_add_function() throws Exception {
        BModel bModel = newBModel();
        B b =bService.addB(mapper.map(bModel, B.class));
        A a = aService.getA(b.getA().getId());
        assertThat(a.toJson()).isEqualTo(b.getA().toJson());
        assertThat(b.toJson()).isEqualTo(bDao.idEquals(b.getId()).querySingle().toJson());
    }

    @Test
    public void test_update_function() throws Exception {
        BModel bModel = newBModel();
        bDao.save(bModel);
        B oldB = mapper.map(bModel,B.class);
        oldB.setA(mapper.map(bModel.getA(),A.class));
        oldB.setName("new_b_name");
        oldB.getA().setName("new_a_name");
        bService.updateB(oldB);
        assertThat(aDao.idEquals(bModel.getA().getId()).querySingle().getName()).isEqualTo("new_a_name");
        assertThat(bDao.idEquals(bModel.getId()).querySingle().getName()).isEqualTo("new_b_name");
    }

    @Test
    public void test_delete_function() throws Exception {
        BModel bModel = newBModel();
        bDao.save(bModel);
        B b = mapper.map(bModel,B.class);
        b.setA(mapper.map(bModel.getA(),A.class));
        bService.deleteB(b.getId());
        assertThat(bDao.idEquals(bModel.getId()).querySingle()).isEqualTo(null);
        assertThat(aDao.idEquals(bModel.getA().getId()).querySingle()).isEqualTo(null);
    }
}