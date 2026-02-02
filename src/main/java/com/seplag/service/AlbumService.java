package com.seplag.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seplag.dto.AlbumTipoDTO;
import com.seplag.model.Album;
import com.seplag.model.AlbumImagem;
import com.seplag.model.Artista;
import com.seplag.repository.AlbumImagemRepository;
import com.seplag.repository.AlbumRepository;
import com.seplag.repository.ArtistaRepository;

@Service
public class AlbumService {

	@Autowired
	private AlbumRepository albumRepository;
	@Autowired
	private ArtistaRepository artistaRepository;
	@Autowired
	private AlbumImagemRepository albumImagemRepository;
	@Autowired
	private MinioService minioService;

	@Transactional
	public Album criar(Album album) {
		return albumRepository.save(album);
	}

	@Transactional
	public Album atualizar(Long id, Album dados) {
		Album album = buscarPorId(id);
		album.setTitulo(dados.getTitulo());
		album.setAno(dados.getAno());
		return albumRepository.save(album);
	}

	public Page<Album> listar(Pageable paginacao) {
		return albumRepository.findAll(paginacao);
	}

	public Album buscarPorId(Long id) {
		return albumRepository.findById(id).orElseThrow(() -> new RuntimeException("Álbum não encontrado: " + id));
	}

	public Page<AlbumTipoDTO> listarPorTipo(String tipo, Pageable paginacao) {
		return albumRepository.buscarAlbunsPorTipoArtista(tipo.toUpperCase(), paginacao);
	}

	@Transactional
	public Album criarComImagem(String titulo, Integer ano, List<Long> artistaIds, MultipartFile imagem)
			throws Exception {

		List<Artista> artistas = artistaRepository.findAllById(artistaIds);

		Album album = new Album();
		album.setTitulo(titulo);
		album.setAno(ano);
		Album albumSalvo = albumRepository.save(album);

		for (Artista artista : artistas) {
			artista.getAlbuns().add(albumSalvo);
		}

		String objectKey = minioService.upload(imagem);

		AlbumImagem img = new AlbumImagem();
		img.setAlbum(albumSalvo);
		img.setObjectKey(objectKey);
		albumImagemRepository.save(img);

		return albumSalvo;
	}

}
