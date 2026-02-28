package com.literalura.litealura.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsumoApi {

    public String obterDados(String endereco) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(endereco, String.class);
    }
}