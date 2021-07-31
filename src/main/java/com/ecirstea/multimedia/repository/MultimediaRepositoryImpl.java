package com.ecirstea.multimedia.repository;

import com.ecirstea.multimedia.model.MultimediaFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class MultimediaRepositoryImpl implements MultimediaRepository {

    @Autowired
    public MultimediaRepositoryImpl(MongoOperations mongoOperations) {
    }

    @Override
    public MultimediaFile save(MultimediaFile multimediaFile) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public MultimediaFile findById(UUID id) {
        return null;
    }

    @Override
    public List<MultimediaFile> findByCategory(String categoryName) {
        return null;
    }

    @Override
    public List<MultimediaFile> findByAuthor(UUID author) {
        return null;
    }

    @Override
    public List<MultimediaFile> findAll() {
        return null;
    }
}
