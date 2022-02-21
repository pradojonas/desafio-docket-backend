package rh.docket.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import rh.docket.desafio.dto.CartorioDTO;
import rh.docket.desafio.service.CartorioService;

@Controller
public class ViewController {

    @Autowired
    CartorioService service;

    @GetMapping("/cartorios")
    public ModelAndView hello(Model model) {
        ModelAndView      mv   = new ModelAndView("cartorios");
        List<CartorioDTO> list = service.list();
        mv.addObject("cartorios", list);
        return mv;
    }

}