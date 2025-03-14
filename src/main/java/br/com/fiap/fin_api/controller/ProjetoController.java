package br.com.fiap.fin_api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.fin_api.model.Integrantes;


@RestController
public class ProjetoController {

    private List<Integrantes> participantes = new ArrayList<>();

    public ProjetoController() {
        participantes.add(new Integrantes( "Nicollas Frei", "RM557647"));
        participantes.add(new Integrantes("Eduardo Eiki", "RM554921"));
        
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        Map<String, Object> response = new HashMap<>();
        response.put("projeto", "Projeto Bank API");
        response.put("integrantes", participantes);

        return ResponseEntity.ok(response);
    }
}
