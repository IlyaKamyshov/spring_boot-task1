package ru.netology.spring_boottask1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootTask1ApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Container
    private static final GenericContainer<?> devapp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    @Container
    private static final GenericContainer<?> prodapp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devapp.start();
        prodapp.start();
    }
    @Test
    void testDev() {
        final String expected = "Current profile is dev";
        int devAppPort = devapp.getMappedPort(8080);

        ResponseEntity<String> devAppEntity = restTemplate.getForEntity(
                "http://localhost:" + devAppPort + "/profile", String.class);

        assertEquals(devAppEntity.getBody(), expected);
    }

    @Test
    void testProd() {
        final String expected = "Current profile is production";
        int prodAppPort = prodapp.getMappedPort(8081);

        ResponseEntity<String> prodAppEntity = restTemplate.getForEntity(
                "http://localhost:" + prodAppPort + "/profile", String.class);

        assertEquals(prodAppEntity.getBody(), expected);
    }

}
