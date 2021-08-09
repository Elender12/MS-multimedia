package com.ecirstea.multimedia.repository;

import com.ecirstea.multimedia.model.MultimediaFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class MultimediaRepositoryImpl implements MultimediaRepository {


    private final MongoOperations mongoOperations;

    @Autowired
    public MultimediaRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public MultimediaFile save(MultimediaFile multimediaFile) {

        return mongoOperations.save(multimediaFile);
    }

    @Override
    public void deleteById(UUID id) {
        Query searchQuery = new Query(Criteria.where("id").is(id));
        this.mongoOperations.remove(searchQuery, MultimediaFile.class);
    }

    @Override
    public MultimediaFile findById(UUID id) {
        return this.mongoOperations.findById(id, MultimediaFile.class);
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
