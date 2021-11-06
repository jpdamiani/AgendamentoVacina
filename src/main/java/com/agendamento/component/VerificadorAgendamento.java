package com.agendamento.component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.agendamento.entities.Agenda;
import com.agendamento.repositories.AgendaRepository;
import com.agendamento.service.NotificacaoService;

@Component
@EnableScheduling
public class VerificadorAgendamento {
	
	
	@Autowired
 	private AgendaRepository _agendaRepository;
	
	@Autowired
	private NotificacaoService notificacao;
	
	private static final String TIME_ZONE = "America/Sao_Paulo";

	/**
	 * Todos os dias, as 00:00 horas ele verifica e dispara os e-mails
	 * */
    @Scheduled(cron = "0 0 0 * * *", zone = TIME_ZONE) 
    public void enviaEmailUmDiaAntes() { 
        System.out.println(LocalDateTime.now());      
        List<Agenda> agendas = _agendaRepository.findAll();
        agendas.stream().filter(agenda -> LocalDate.now().plusDays(1)
        		.isEqual(agenda.getDataAgendamento().toLocalDate()))
        		.forEach(agenda -> notificacao.sendMailLembrete(agenda));       
    }
}
