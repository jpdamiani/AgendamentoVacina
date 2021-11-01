package com.agendamento.service;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.agendamento.entities.Agenda;
import com.agendamento.entities.Usuario;
import com.agendamento.repositories.UsuarioRepository;

@Service
public class NotificacaoService {

	@Autowired 
	private JavaMailSender mailSender;

	@Autowired 
	private UsuarioRepository _usuarioRepository;


	public String sendMailConfirmacao(Agenda agenda) {
		Usuario usuario = _usuarioRepository.findById(agenda.getUsuario().getId()).get();

		SimpleMailMessage message = new SimpleMailMessage();

		message.setText("Confirmação de Vacinação no dia ".concat(agenda.getDataAgendamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss"))).concat(" horas"));

		message.setSubject("Confirmação de agendamento");
		message.setTo(usuario.getEmail());
		message.setFrom("grupo4ds3@gmail.com");

		try {
			mailSender.send(message);
			return "Email enviado com sucesso!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao enviar email.";
		}
	}


	public String sendMailLembrete(Agenda agenda) {
		Usuario usuario = _usuarioRepository.findById(agenda.getUsuario().getId()).get();

		SimpleMailMessage message = new SimpleMailMessage();

		message.setText("Você tem vacinação amanhã, no dia ".concat(agenda.getDataAgendamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss"))).concat(" horas"));

		message.setSubject("Lembrete de Agendamento");
		message.setTo(usuario.getEmail());
		message.setFrom("grupo4ds3@gmail.com");

		try {
			mailSender.send(message);
			return "Email enviado com sucesso!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao enviar email.";
		}
	}
	
}
