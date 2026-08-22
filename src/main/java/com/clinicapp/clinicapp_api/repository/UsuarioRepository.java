package com.clinicapp.clinicapp_api.repository;
// ============================================================
// REPOSITORIO: UsuarioRepository
// ============================================================

import com.clinicapp.clinicapp_api.entity.Usuario;
import com.clinicapp.clinicapp_api.utils.Queries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query(value = Queries.VERIFICAR_CREDENCIALES, nativeQuery = true)
    Long verificarCredenciales(@Param("username") String username, @Param("password") String password);

    @Query(value = Queries.OBTENER_ROL_USUARIO, nativeQuery = true)
    String obtenerRolUsuario(@Param("username") String username);
}
