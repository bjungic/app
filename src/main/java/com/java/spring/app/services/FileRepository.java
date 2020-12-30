package com.java.spring.app.services;

import com.java.spring.app.model.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {


}
