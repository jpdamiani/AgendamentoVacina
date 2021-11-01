package com.agendamento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendamento.entities.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Long>{

}
