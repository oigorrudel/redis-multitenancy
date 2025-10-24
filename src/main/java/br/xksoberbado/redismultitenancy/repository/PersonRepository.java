package br.xksoberbado.redismultitenancy.repository;

import br.xksoberbado.redismultitenancy.model.Person;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface PersonRepository extends ListCrudRepository<Person, UUID> {
}
