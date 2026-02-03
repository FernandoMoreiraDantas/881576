package com.seplag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.seplag.model.Artista;
import com.seplag.repository.ArtistaRepository;
import com.seplag.service.ArtistaService;

@ExtendWith(MockitoExtension.class)
class ArtistaServiceTest {

    @Mock
    private ArtistaRepository artistaRepository;

    @InjectMocks
    private ArtistaService artistaService;

    @Test
    void deveBuscarArtistaPorId() {
        Artista artista = new Artista();
        artista.setId(1L);
        artista.setNome("Roberto Carlos");

        when(artistaRepository.findById(1L)).thenReturn(Optional.of(artista));

        Artista resultado = artistaService.buscarPorId(1L);

        assertEquals("Roberto Carlos", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoArtistaNaoEncontrado() {
        when(artistaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> artistaService.buscarPorId(99L));
    }
}

