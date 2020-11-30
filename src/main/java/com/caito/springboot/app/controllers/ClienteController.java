package com.caito.springboot.app.controllers;

import com.caito.springboot.app.models.entity.Cliente;
import com.caito.springboot.app.models.service.IClienteService;
import com.caito.springboot.app.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model){

        Pageable pageRequest = PageRequest.of(page,5);
        Page<Cliente> clientes = clienteService.findAl(pageRequest);
        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
        model.addAttribute("titulo","Listado de Clientes");
        model.addAttribute("clientes",clientes);
        model.addAttribute("page", pageRender);
        return "listar";
    }

    @RequestMapping(value = "/form")
    public String crear(Map<String, Object> model){

        Cliente cliente = new Cliente();
        model.put("titulo","Formulario de Cliente");
        model.put("cliente", cliente);

        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid  Cliente cliente, BindingResult result, Model model,
                          RedirectAttributes flash){

        if(result.hasErrors()){
            model.addAttribute("titulo","Formulario de Cliente");
            return "form";
        }
        clienteService.save(cliente);
        flash.addFlashAttribute("success","Cliente Guardado !");
        return "redirect:listar";
    }

    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable(name = "id") long id, Map<String, Object> model){

        Cliente cliente = null;

        if(id > 0){
            cliente = clienteService.findOne(id);
        }else {
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Editar Cliente");
        return "form";
    }

    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(name = "id") long id, RedirectAttributes flash){

        if(id > 0){
            clienteService.delete(id);
            flash.addFlashAttribute("success","Cliente eliminado !");
        }
        return "redirect:/listar";
    }
}
