package test.functional.product;

import com.eureka2.shading.codehaus.jackson.JsonParseException;
import com.eureka2.shading.codehaus.jackson.map.ObjectMapper;
import com.thoughtworks.gaia.GaiaApplication;
import com.thoughtworks.gaia.common.constant.EnvProfile;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.product.endpoint.AEndPoint;
import com.thoughtworks.gaia.product.entity.A;
import com.thoughtworks.gaia.product.service.AService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(GaiaApplication.class)
@Rollback
@Transactional
@ActiveProfiles({EnvProfile.TEST})
public class AEndPointFunctionalTest{
    @Autowired
    private AService aService;
    @Autowired
    private AEndPoint aEndPoint;

    private A newA(){
        A a = new A();
        a.setName("a_name");
        return a;
    }
    @Test
    public void should_get_a_with_id() throws Exception {
        A a = aService.addA(newA());
        Response response = aEndPoint.getA(a.getId());
        String entityStr = new ObjectMapper().writeValueAsString(response.getEntity());
        assertThat(entityStr).isEqualTo(a.toJson());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_exception_when_not_found() throws Exception {
        aEndPoint.getA(1L);
    }

//    @Test
//    public void should_add_a_and_return_added_a() throws Exception {
//        Response response = aEndPoint.addA(newA().toJson());
//        String entityStr = new ObjectMapper().writeValueAsString(response.getEntity());
//        A a = aService.getA(1L);
//        assertThat(a.toJson()).isEqualTo(entityStr);
//    }

    @Test(expected = JsonParseException.class)
    public void should_throw_exception_when_not_match_in_add_method() throws Exception {
        aEndPoint.addA("a wrong json");
    }

//    @Test
//    public void should_update_a_and_return_updated_a() throws Exception {
//        A oldA=aService.addA(newA());
//        A newA = newA();
//        newA.setName("new_name");
//        Response response = aEndPoint.updateA(oldA.getId(),newA.toJson());
//        String entityStr = new ObjectMapper().writeValueAsString(response.getEntity());
//        A a = aService.getA(1L);
//        assertThat(a.toJson()).isEqualTo(entityStr);
//    }

    @Test(expected = JsonParseException.class)
    public void should_throw_exception_when_not_match_in_update_method() throws Exception {
        aEndPoint.updateA(1L,"a wrong json");
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_exception_when_not_found_in_update_method() throws Exception {
        aEndPoint.updateA(1L,newA().toJson());
    }

    @Test
    public void delete_method() throws Exception {

    }
    @Test
    public void should_throw_exception_when_not_found_in_delete() throws Exception {


    }


}