package test.functional.product;

import com.thoughtworks.gaia.GaiaApplication;
import com.thoughtworks.gaia.common.constant.EnvProfile;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.product.entity.A;
import com.thoughtworks.gaia.product.service.AService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by heming on 4/8/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(GaiaApplication.class)
@Rollback
@Transactional
@ActiveProfiles({EnvProfile.TEST})
@WebAppConfiguration
public class AEndPointTest {
    @Autowired
    private AService aService;

    private String url = "http://localhost:8080/gaia/rest";
    private Client client = ClientBuilder.newClient();

    public A getAModel() {
        A a = new A();
        a.setName("name");
        a.setTime_created(new Date());
        return a;
    }

    @Test
    public void getA() throws Exception {
        WebTarget target = client.target(url + "/As/1");
        Response response = target.request().buildGet().invoke();
        assertThat(response.readEntity(A.class).getId()).isEqualTo(1L);
        response.close();
    }


    @Test
    public void addA() throws Exception {
        WebTarget target = client.target(url + "/As");
        A a = new A();
        a.setName("name2");
        Entity<A> entity = Entity.entity(a, MediaType.APPLICATION_JSON);
        Response response = target.request()
                .buildPost(entity)
                .invoke();
        A reponseA = response.readEntity(A.class);
        assertThat(reponseA.getId() != null);
        assertThat(reponseA.getName()).isEqualTo("name2");
        response.close();
    }

    @Test
    public void updateA() throws Exception {
        WebTarget target = client.target(url + "/As/1");
        A a = new A();
        a.setName("name3");
        a.setTime_created(new Date());
        Entity<A> entity = Entity.entity(a, MediaType.APPLICATION_JSON);
        Response response = target.request().buildPut(entity).invoke();
        A reponseA = response.readEntity(A.class);
        assertThat(reponseA.getId()).isEqualTo(1L);
        assertThat(reponseA.getName()).isEqualTo("name3");
        response.close();
    }

    @Test(expected = NotFoundException.class)
    public void delA() throws Exception {
        WebTarget target = client.target(url + "As/1");
        Response response = target.request().buildDelete().invoke();
        aService.getA(1L);
        response.close();
    }

}