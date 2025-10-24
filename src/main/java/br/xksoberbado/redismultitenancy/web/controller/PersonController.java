package br.xksoberbado.redismultitenancy.web.controller;

import br.xksoberbado.redismultitenancy.model.Person;
import br.xksoberbado.redismultitenancy.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository repository;

    @GetMapping
    public List<Person> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public void create(@RequestBody Person person) {
        repository.save(person);
    }
}
