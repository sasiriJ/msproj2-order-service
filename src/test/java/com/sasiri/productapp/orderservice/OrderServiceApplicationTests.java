package com.sasiri.productapp.orderservice;

import com.sasiri.productapp.orderservice.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {
    @ServiceConnection
    static MySQLContainer mySQLContainer = new MySQLContainer();

    @LocalServerPort
    private int port;


    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void shouldCreateOrder() {
        String reqBody = """
                {
                    "skuCode" : "IPhone_15",
                    "price": 1500,
                    "quantity": 1
                }
                """;
        InventoryClientStub.stubInventoryCall("IPhone_15", 10);
        RestAssured.given().contentType(ContentType.JSON)
                .body(reqBody)
                .when().post("/api/orders")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .body()
                .toString();
    }

}
