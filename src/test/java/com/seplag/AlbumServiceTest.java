package com.seplag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.seplag.model.Album;
import com.seplag.model.Artista;
import com.seplag.repository.AlbumImagemRepository;
import com.seplag.repository.AlbumRepository;
import com.seplag.repository.ArtistaRepository;
import com.seplag.service.AlbumService;
import com.seplag.service.MinioService;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ArtistaRepository artistaRepository;

    @Mock
    private AlbumImagemRepository albumImagemRepository;

    @Mock
    private MinioService minioService;

    @InjectMocks
    private AlbumService albumService;

    @Test
    void deveCriarAlbumComImagemEArtistas() throws Exception {

        MultipartFile imagem = mock(MultipartFile.class);

        when(minioService.upload(imagem)).thenReturn("arquivo.png");

        Artista artista = new Artista();
        artista.setId(1L);

        when(artistaRepository.findAllById(any()))
                .thenReturn(List.of(artista));

        when(albumRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        Album album = albumService.criarComImagem(
                "Novo Album",
                2025,
                List.of(1L),
                imagem
        );

        assertEquals("Novo Album", album.getTitulo());

        verify(minioService).upload(imagem);
        verify(albumImagemRepository).save(any());
    }
}
