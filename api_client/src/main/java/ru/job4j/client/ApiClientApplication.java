package ru.job4j.client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ApiClientApplication {

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiClientApplication.class, args);
    }

}
