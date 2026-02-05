package com.seplag.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seplag.client.RegionalClient;
import com.seplag.dto.RegionalExternoDTO;
import com.seplag.model.Regional;
import com.seplag.repository.RegionalRepository;

@Service
public class RegionalImportService {

    private final RegionalRepository regionalRepository;
    private final RegionalClient regionalClient;

    public RegionalImportService(RegionalRepository regionalRepository,
                                 RegionalClient regionalClient) {
        this.regionalRepository = regionalRepository;
        this.regionalClient = regionalClient;
    }

    @Transactional
    public void sincronizarRegionais() {

        List<RegionalExternoDTO> externas = regionalClient.buscarRegionais();

        // Mapa rápido pelo id externo
        Map<Integer, RegionalExternoDTO> mapaExternas = externas.stream()
                .collect(Collectors.toMap(RegionalExternoDTO::getId, Function.identity()));

        // Percorre todas do banco
        List<Regional> internas = regionalRepository.findAll();

        for (Regional regional : internas) {

            RegionalExternoDTO dto = mapaExternas.get(regional.getId());

            // Não veio no endpoint → inativa
            if (dto == null) {
                regional.setAtivo(false);
                continue;
            }

            // Nome mudou → versiona
            if (!regional.getNome().equals(dto.getNome()) && regional.getAtivo()) {

                // inativa o antigo
                regional.setAtivo(false);

                // cria novo registro
                Regional novo = new Regional();
                novo.setId(dto.getId());
                novo.setNome(dto.getNome());
                novo.setAtivo(true);

                regionalRepository.save(novo);
            }

            // remove do mapa para sobrar só os novos
            mapaExternas.remove(regional.getId());
        }

        // O que sobrou no mapa → não existia no banco
        for (RegionalExternoDTO dto : mapaExternas.values()) {

            Regional novo = new Regional();
            novo.setId(dto.getId());
            novo.setNome(dto.getNome());
            novo.setAtivo(true);

            regionalRepository.save(novo);
        }
    }

}
