package com.seplag.client;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.seplag.dto.RegionalExternoDTO;

@Service
public class RegionalClient {

    private final WebClient webClient;

    public RegionalClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<RegionalExternoDTO> buscarRegionais() {
        return webClient.get()
                .uri("https://integrador-argus-api.geia.vip/v1/regionais")
                .retrieve()
                .bodyToFlux(RegionalExternoDTO.class)
                .collectList()
                .block();
    }
}
