package com.eventoapp.eventoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.eventoapp.models.Evento;
import com.eventoapp.eventoapp.models.Convidado;
import com.eventoapp.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.eventoapp.repository.EventoRepository;

@Controller
public class EventoController {
    
    @Autowired
    private EventoRepository er;

    @Autowired
    private ConvidadoRepository cr;

    @GetMapping("/cadastrarEvento")
    public String form(){
        return "evento/formEvento";
    }
    @PostMapping("/cadastrarEvento")
    public String form(Evento evento){

        er.save(evento);

        return "redirect:/cadastrarEvento";
    }
    @GetMapping("/eventos")
    public ModelAndView listaEventos(){
        ModelAndView mv= new ModelAndView("index.html");
        Iterable<Evento> eventos = er.findAll();
        mv.addObject("evento", eventos);
        return mv;
    }

    @GetMapping("/{codigo}")
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
        Evento evento = er.findByCodigo(codigo);
        ModelAndView mv= new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento", evento);
        return mv;
    }
    @PostMapping("/{codigo}")
    public String detalhesEventoPost(@PathVariable("codigo") long codigo, Convidado convidado){
        Evento evento = er.findByCodigo(codigo);
        convidado.setEvento(evento);
        cr.save(convidado);
        return "redirect:/{codigo}";
    }
}
