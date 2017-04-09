package test.functional.product;

import com.thoughtworks.gaia.GaiaApplication;
import com.thoughtworks.gaia.common.constant.EnvProfile;
import com.thoughtworks.gaia.product.entity.A;
import com.thoughtworks.gaia.product.entity.B;
import com.thoughtworks.gaia.product.service.AService;
import com.thoughtworks.gaia.product.service.BService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(GaiaApplication.class)
@Rollback
@ActiveProfiles({EnvProfile.TEST})
@WebAppConfiguration
public class BEndPointTest {
    @Autowired
    private BService bService;
    @Autowired
    private AService aService;

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    private Client client = ClientBuilder.newClient();
    private static String url = "http://localhost:8080/gaia/rest/Bs";

    public B newB() {
        B b = new B();
        b.setName("b_name");
        b.setTime_created(new Date());
        b.setA(newA());
        return b;
    }

    public A newA() {
        A a = new A();
        a.setName("a_name");
        a.setTime_created(new Date());
        return a;
    }

    @Test
    public void test_add() throws Exception {
        WebTarget target = client.target(url);
        B b = newB();
        Entity<B> entity = Entity.entity(b, MediaType.APPLICATION_JSON);
        Response response = target.request()
                .buildPost(entity)
                .invoke();
        B responseB = response.readEntity(B.class);
        assertThat(responseB).isNotEqualTo(null);
        assertThat(responseB.getName()).isEqualTo(b.getName());
        response.close();
    }

    @Test
    public void test_get() throws Exception {
        WebTarget target = client.target(url + "/1");
        Response response = target.request().buildGet().invoke();
        assertThat(response.readEntity(B.class).getId()).isEqualTo(1L);
        response.close();
    }

    @Test
    public void test_upd() throws Exception {
        WebTarget target = client.target(url + "/1");
        B b = newB();
        Entity<B> entity = Entity.entity(b, MediaType.APPLICATION_JSON);
        Response response = target.request()
                .buildPut(entity)
                .invoke();
        B responseB = response.readEntity(B.class);
        assertThat(responseB).isNotEqualTo(null);
        assertThat(responseB.getName()).isEqualTo(b.getName());
        assertThat(responseB.getA().getName()).isEqualTo(b.getA().getName());
        response.close();
    }

    @Test
    public void test_del_b() throws Exception {
        WebTarget target = client.target(url + "/1");
        Response response = target.request().buildDelete().invoke();
        B responseB = response.readEntity(B.class);
        assertThat(responseB.getId()).isEqualTo(1L);
        response.close();
    }

}