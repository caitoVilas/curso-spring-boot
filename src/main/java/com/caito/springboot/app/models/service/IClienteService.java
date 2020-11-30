package com.caito.springboot.app.models.service;

import com.caito.springboot.app.models.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {

    public List<Cliente> findAll();
    public Page<Cliente> findAl(Pageable pageable);
    public void save(Cliente cliente);
    public Cliente findOne(long id);
    public void delete(long id);
}
