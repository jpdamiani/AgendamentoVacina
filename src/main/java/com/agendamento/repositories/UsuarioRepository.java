package com.agendamento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendamento.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
