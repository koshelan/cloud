package netology.cloud;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    public static GenericContainer<?> app = new GenericContainer<>("cloud_cloud_app")
            .withExposedPorts(5500);

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeAll
    public static void setUp() {
        app.start();
    }

    @Test
    void contextLoadsDevApp() {
        var forEntity =
                restTemplate.getForEntity("http://localhost:" + app.getMappedPort(5500)+"/list?limit=3", Object.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, forEntity.getStatusCode());
    }


}
