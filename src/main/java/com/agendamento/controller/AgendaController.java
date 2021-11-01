package com.agendamento.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agendamento.entities.Agenda;
import com.agendamento.repositories.AgendaRepository;
import com.agendamento.service.NotificacaoService;

@RestController
public class AgendaController {

	@Autowired
	private AgendaRepository _agendaRepository;

	@Autowired
	private NotificacaoService _notificacaoService;
	
	@RequestMapping(value = "/agenda", method = RequestMethod.GET)
	public List<Agenda> Get() {
		return _agendaRepository.findAll();
	}


	@RequestMapping(value = "/agenda/{id}", method = RequestMethod.GET) 
	public ResponseEntity<Agenda> GetById(@PathVariable(value = "id") long id) {
		Optional<Agenda> agenda = _agendaRepository.findById(id); 
		if(agenda.isPresent()) 
			return new ResponseEntity<Agenda>(agenda.get(),HttpStatus.OK); 
		else 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	}


	@RequestMapping(value = "/agenda", method = RequestMethod.POST) 
	public Agenda Post(@Validated @RequestBody Agenda agenda) { 
		_notificacaoService.sendMailConfirmacao(agenda);
		return _agendaRepository.save(agenda); 
	}



	@RequestMapping(value = "/agenda/{id}", method = RequestMethod.PUT) 
	public ResponseEntity<Agenda> Put(@PathVariable(value = "id") long id, @Validated @RequestBody Agenda newAgenda) { 
		Optional<Agenda> oldAgenda = _agendaRepository.findById(id); 
		if(oldAgenda.isPresent()){ 
			Agenda agenda = oldAgenda.get(); 
			agenda.setDataAgendamento(newAgenda.getDataAgendamento());
			_agendaRepository.save(agenda);

			return new ResponseEntity<Agenda>(agenda, HttpStatus.OK); 
		} 
		else 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); }


	@RequestMapping(value = "/agenda/{id}", method = RequestMethod.DELETE) 
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id) {
		Optional<Agenda> agenda = _agendaRepository.findById(id);
		if(agenda.isPresent()){ 
			_agendaRepository.delete(agenda.get()); 
			return new
					ResponseEntity<>(HttpStatus.OK); 
		} else 
			return new
					ResponseEntity<>(HttpStatus.NOT_FOUND); 
	}


}
