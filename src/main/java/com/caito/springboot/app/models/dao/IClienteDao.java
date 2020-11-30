package com.caito.springboot.app.models.dao;

import com.caito.springboot.app.models.entity.Cliente;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {


}
