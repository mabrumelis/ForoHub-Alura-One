CREATE TABLE respuestas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mensaje TEXT NOT NULL,
    topico INT,
    fechaCreacion DATETIME NOT NULL,
    autor INT,
    solucion BOOLEAN,
    FOREIGN KEY (topico) REFERENCES Topico(id),
    FOREIGN KEY (autor) REFERENCES Usuario(id)
);
