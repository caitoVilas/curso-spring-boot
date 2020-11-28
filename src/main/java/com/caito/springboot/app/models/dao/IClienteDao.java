package com.caito.springboot.app.models.dao;

import com.caito.springboot.app.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IClienteDao extends CrudRepository<Cliente, Long> {


}
