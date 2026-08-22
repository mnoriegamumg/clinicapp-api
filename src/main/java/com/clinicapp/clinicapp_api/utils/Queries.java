package com.clinicapp.clinicapp_api.utils;
// ============================================================
// UTILS: Queries.java
// ============================================================

public final class Queries {

    private Queries() {
        // Constructor privado para evitar instanciación
    }

    // ============================================================
    // QUERIES PARA PACIENTES
    // ============================================================
    public static final String BUSCAR_PACIENTES_POR_NOMBRE =
            "SELECT * FROM pacientes " +
                    "WHERE CONCAT(nombre, ' ', apellido) LIKE CONCAT('%', :busqueda, '%') " +
                    "ORDER BY nombre ASC";

    public static final String CONTAR_PACIENTES_TOTAL =
            "SELECT COUNT(*) FROM pacientes";

    // ============================================================
    // QUERIES PARA MEDICOS
    // ============================================================
    public static final String BUSCAR_MEDICOS_POR_ESPECIALIDAD =
            "SELECT * FROM medicos " +
                    "WHERE especialidad LIKE CONCAT('%', :especialidad, '%') " +
                    "ORDER BY nombre ASC";

    public static final String CONTAR_MEDICOS_TOTAL =
            "SELECT COUNT(*) FROM medicos";

    public static final String LISTAR_ESPECIALIDADES =
            "SELECT DISTINCT especialidad FROM medicos ORDER BY especialidad ASC";

    public static final String BUSCAR_MEDICOS_POR_NOMBRE =
            "SELECT * FROM medicos " +
                    "WHERE CONCAT(nombre, ' ', apellido) LIKE CONCAT('%', :busqueda, '%') " +
                    "ORDER BY nombre ASC";

    // ============================================================
    // QUERIES PARA CITAS
    // ============================================================
    public static final String VERIFICAR_DISPONIBILIDAD =
            "SELECT COUNT(*) FROM citas " +
                    "WHERE medico_id = :medicoId " +
                    "AND fecha_hora = :fechaHora " +
                    "AND estado != 'CANCELADA'";

    public static final String LISTAR_CITAS_POR_MEDICO_Y_FECHA =
            "SELECT c.* FROM citas c " +
                    "WHERE c.medico_id = :medicoId " +
                    "AND DATE(c.fecha_hora) = :fecha " +
                    "AND c.estado != 'CANCELADA' " +
                    "ORDER BY c.fecha_hora ASC";

    public static final String LISTAR_CITAS_POR_RANGO_FECHAS =
            "SELECT c.* FROM citas c " +
                    "WHERE DATE(c.fecha_hora) BETWEEN :inicio AND :fin " +
                    "ORDER BY c.fecha_hora ASC";

    public static final String CONTAR_CITAS_POR_ESTADO =
            "SELECT COUNT(*) FROM citas WHERE estado = :estado";

    public static final String CONTAR_CITAS_POR_MEDICO =
            "SELECT COUNT(*) FROM citas WHERE medico_id = :medicoId AND estado != 'CANCELADA'";

    public static final String CONTAR_CITAS_HOY =
            "SELECT COUNT(*) FROM citas WHERE DATE(fecha_hora) = CURDATE() AND estado != 'CANCELADA'";

    public static final String CITAS_DEL_DIA =
            "SELECT c.id, p.nombre AS paciente_nombre, p.apellido AS paciente_apellido, " +
                    "m.nombre AS medico_nombre, m.apellido AS medico_apellido, " +
                    "c.fecha_hora, c.estado, c.motivo " +
                    "FROM citas c " +
                    "JOIN pacientes p ON c.paciente_id = p.id " +
                    "JOIN medicos m ON c.medico_id = m.id " +
                    "WHERE DATE(c.fecha_hora) = CURDATE() " +
                    "AND c.estado != 'CANCELADA' " +
                    "ORDER BY c.fecha_hora ASC";

    // ============================================================
    // QUERIES PARA USUARIOS (Autenticación)
    // ============================================================
    public static final String VERIFICAR_CREDENCIALES =
            "SELECT COUNT(*) FROM usuarios " +
                    "WHERE username = :username " +
                    "AND password = :password " +
                    "AND activo = 1";

    public static final String OBTENER_ROL_USUARIO =
            "SELECT rol FROM usuarios WHERE username = :username";
}
