package com.gestion.clientes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.clientes.exception.ResourceNotFoundException;
import com.gestion.clientes.model.Cliente;
import com.gestion.clientes.repository.ClienteRepository;

@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")

public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }

    @PostMapping("/clientes")
    public Cliente guardarCliente(@RequestBody Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> listarClientePorId(@PathVariable Long id){
    Cliente cliente = clienteRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("El cliente con ese ID no existe : " + id));
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteRequest){
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("El cliente con ese ID no existe : " + id));

            cliente.setNombre(clienteRequest.getNombre());
            cliente.setApellido(clienteRequest.getApellido());
            cliente.setEmail(clienteRequest.getEmail());

            Cliente clienteActualizado = clienteRepository.save(cliente);

            return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarCliente(@PathVariable Long id){
        Cliente cliente = clienteRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("El cliente con ese ID no existe : " + id));
        clienteRepository.delete(cliente);
        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
