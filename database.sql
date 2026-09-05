-- ============================================================
-- SCRIPT DE BASE DE DATOS - CLINICAPP
-- ============================================================
-- Este archivo debe ejecutarse en MySQL (XAMPP)
-- ============================================================

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS clinica_db;
USE clinica_db;

-- ============================================================
-- TABLA: pacientes
-- ============================================================
CREATE TABLE IF NOT EXISTS pacientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    telefono VARCHAR(20),
    fecha_nacimiento DATE,
    direccion VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- TABLA: medicos
-- ============================================================
CREATE TABLE IF NOT EXISTS medicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    telefono VARCHAR(20),
    horario_inicio VARCHAR(10),
    horario_fin VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- TABLA: citas
-- ============================================================
CREATE TABLE IF NOT EXISTS citas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    motivo VARCHAR(255),
    estado ENUM('PENDIENTE', 'CONFIRMADA', 'ATENDIDA', 'CANCELADA') DEFAULT 'PENDIENTE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id) ON DELETE CASCADE,
    FOREIGN KEY (medico_id) REFERENCES medicos(id) ON DELETE CASCADE,
    UNIQUE KEY uk_medico_fecha (medico_id, fecha_hora)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- TABLA: usuarios (para autenticación)
-- ============================================================
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol ENUM('RECEPCIONISTA', 'MEDICO', 'ADMIN') NOT NULL DEFAULT 'RECEPCIONISTA',
    medico_id BIGINT NULL,
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (medico_id) REFERENCES medicos(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- DATOS DE PRUEBA (Seeders)
-- ============================================================

-- Insertar médicos
INSERT INTO medicos (nombre, apellido, especialidad, email, telefono, horario_inicio, horario_fin) VALUES
('Ana', 'Rodríguez', 'Medicina General', 'ana.rodriguez@clinica.com', '5554-5678', '08:00', '16:00'),
('José', 'López', 'Cardiología', 'jose.lopez@clinica.com', '5555-6789', '08:00', '16:00'),
('Elena', 'Sánchez', 'Pediatría', 'elena.sanchez@clinica.com', '5556-7890', '08:00', '16:00');

-- Insertar pacientes
INSERT INTO pacientes (nombre, apellido, email, telefono, fecha_nacimiento, direccion) VALUES
('Carlos', 'Pérez', 'carlos.perez@email.com', '5551-2345', '1985-06-15', 'Calle Principal #123'),
('María', 'González', 'maria.g@email.com', '5552-3456', '1990-11-20', 'Avenida Central #456'),
('Luis', 'Martínez', 'luis.m@email.com', '5553-4567', '1978-03-10', 'Calle Secundaria #789'),
('Ana', 'Torres', 'ana.torres@email.com', '5554-5678', '1995-07-25', 'Boulevard Norte #321'),
('Roberto', 'Méndez', 'roberto.m@email.com', '5555-6789', '1982-09-12', 'Calle Oriente #654');

-- Insertar usuarios (contraseña: 12345678 encriptada con BCrypt)
INSERT INTO usuarios (username, password, rol, medico_id, activo) VALUES
('recepcionista', '$2a$10$rT.p8l7Jk1ZqQhQzPzAqXe1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6p7q8r9', 'RECEPCIONISTA', NULL, TRUE),
('dr.jose.lopez', '$2a$10$rT.p8l7Jk1ZqQhQzPzAqXe1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6p7q8r9', 'MEDICO', 2, TRUE),
('dra.ana.rodriguez', '$2a$10$rT.p8l7Jk1ZqQhQzPzAqXe1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6p7q8r9', 'MEDICO', 1, TRUE),
('admin', '$2a$10$rT.p8l7Jk1ZqQhQzPzAqXe1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6p7q8r9', 'ADMIN', NULL, TRUE);

-- Insertar citas de ejemplo
INSERT INTO citas (paciente_id, medico_id, fecha_hora, motivo, estado) VALUES
(1, 1, DATE_ADD(NOW(), INTERVAL 1 DAY), 'Consulta general - Dolor de cabeza', 'CONFIRMADA'),
(2, 2, DATE_ADD(NOW(), INTERVAL 1 DAY), 'Revisión cardiológica', 'PENDIENTE'),
(3, 3, DATE_ADD(NOW(), INTERVAL 1 DAY), 'Control pediátrico', 'CONFIRMADA'),
(4, 1, DATE_ADD(NOW(), INTERVAL 2 DAY), 'Chequeo general', 'PENDIENTE'),
(5, 2, DATE_ADD(NOW(), INTERVAL 2 DAY), 'Electrocardiograma', 'CONFIRMADA'),
(1, 3, DATE_ADD(NOW(), INTERVAL 3 DAY), 'Seguimiento pediátrico', 'PENDIENTE');

ALTER TABLE citas
ADD COLUMN diagnostico TEXT,
ADD COLUMN comentarios_medico TEXT,
ADD COLUMN tratamiento TEXT,
ADD COLUMN fecha_atencion DATETIME;