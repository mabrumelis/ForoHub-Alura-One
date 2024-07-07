CREATE TABLE forohub.topico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    mensaje TEXT NOT NULL,
    fechaCreacion DATETIME NOT NULL,
    status ENUM('abierto', 'cerrado') NOT NULL,
    autor BIGINT,
    curso BIGINT,
    respuestas INT,
    FOREIGN KEY (autor) REFERENCES usuario(id),
    FOREIGN KEY (curso) REFERENCES courso(id)
);
