package com.safina.lyudmila.tests.testcontainers;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;
import static org.testcontainers.shaded.org.hamcrest.core.IsEqual.equalTo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@Testcontainers
public class TestContainerDemoTest {


    @Container
    public MockServerContainer mockServer = new MockServerContainer(DockerImageName.parse("mockserver/mockserver"));
    public MockServerClient mockServerClient;

    @BeforeEach
    public void setUp() {
        mockServerClient = new MockServerClient(mockServer.getHost(), mockServer.getServerPort());
        mockServerClient 
                .when(request()
                        .withPath("/person")
                        .withQueryStringParameter("name", "peter"))
                .respond(response()
                        .withBody("Peter the person!"));
    }

    @AfterEach
    public void tearDown() {
        mockServer.stop();
        mockServerClient.close();
    }

    @Test
    public void mockServerTest() throws IOException, InterruptedException {


        System.out.println(mockServer.getHost());

        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("http://" + mockServer.getHost() + ":" + mockServer.getServerPort() + "/person?name=peter"))
                .GET()
                .build();

        java.net.http.HttpResponse<String> response = java.net.http.HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());

        assertThat("Name received", response.body(), equalTo("Peter the person!"));

    }
}
