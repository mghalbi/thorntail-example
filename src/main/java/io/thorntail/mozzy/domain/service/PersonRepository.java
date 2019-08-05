package io.thorntail.mozzy.domain.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.apache.deltaspike.data.api.EntityPersistenceRepository;
import org.apache.deltaspike.data.api.Repository;

import io.thorntail.mozzy.domain.model.Person;


@Repository
public interface PersonRepository extends EntityPersistenceRepository<Person, Long> {

    Stream<Person> findByNameLikeAndDocumentId(String name, String documentId);

    Stream<Person> findByNameLike(String name);

    Stream<Person> findByDocumentId(String documentId);

    Stream<Person> findTop10OrderByName();

    Optional<Person> findBy(Long id);

}
