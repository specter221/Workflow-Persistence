package com.sr.repo;

import org.springframework.data.repository.CrudRepository;

import com.sr.domain.PersistenceModel;

public interface PersistenceRepo extends CrudRepository<PersistenceModel, Integer> {

}
