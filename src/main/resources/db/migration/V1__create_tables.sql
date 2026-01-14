-- 1. Criação das Tabelas
CREATE TABLE artista (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(200) NOT NULL,
  tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('CANTOR', 'BANDA')),
  created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE album (
  id BIGSERIAL PRIMARY KEY,
  titulo VARCHAR(200) NOT NULL,
  ano INTEGER,
  created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE artista_album (
  artista_id BIGINT REFERENCES artista(id),
  album_id BIGINT REFERENCES album(id),
  PRIMARY KEY (artista_id, album_id)
);

CREATE TABLE album_imagem (
  id BIGSERIAL PRIMARY KEY,
  album_id BIGINT REFERENCES album(id),
  object_key VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE regional (
  id INTEGER PRIMARY KEY,
  nome VARCHAR(200) NOT NULL,
  ativo BOOLEAN DEFAULT TRUE
);

-- 2. Inserção de Dados (Seeds)

-- Artistas
INSERT INTO artista (nome, tipo) VALUES ('Roberto Carlos', 'CANTOR');
INSERT INTO artista (nome, tipo) VALUES ('Legião Urbana', 'BANDA');
INSERT INTO artista (nome, tipo) VALUES ('Marisa Monte', 'CANTOR');

-- Álbuns
INSERT INTO album (titulo, ano) VALUES ('O Insubstituível', 2005);
INSERT INTO album (titulo, ano) VALUES ('Dois', 1986);
INSERT INTO album (titulo, ano) VALUES ('Memórias, Crônicas e Declarações de Amor', 2000);

-- Relacionamentos Artista_Album (N:N)
INSERT INTO artista_album (artista_id, album_id) VALUES (1, 1);
INSERT INTO artista_album (artista_id, album_id) VALUES (2, 2);
INSERT INTO artista_album (artista_id, album_id) VALUES (3, 3);

-- Imagens de Álbum (Caminhos simulados para o MinIO)
INSERT INTO album_imagem (album_id, object_key) VALUES (1, 'capas/roberto_2005.jpg');
INSERT INTO album_imagem (album_id, object_key) VALUES (2, 'capas/legiao_dois.png');
INSERT INTO album_imagem (album_id, object_key) VALUES (3, 'capas/marisa_monte.webp');

-- Regionais
INSERT INTO regional (id, nome, ativo) VALUES (1, 'Sudeste', true);
INSERT INTO regional (id, nome, ativo) VALUES (2, 'Nordeste', true);
INSERT INTO regional (id, nome, ativo) VALUES (3, 'Sul', true);