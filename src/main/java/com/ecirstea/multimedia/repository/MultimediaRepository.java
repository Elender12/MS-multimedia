package com.ecirstea.multimedia.repository;

import com.ecirstea.multimedia.model.MultimediaFile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
@Repository
public interface MultimediaRepository {

     MultimediaFile save(MultimediaFile multimediaFile );

     void deleteById( UUID id );

     MultimediaFile findById( UUID id );

     List<MultimediaFile> findByCategory(String categoryName);

     List<MultimediaFile> findByAuthor(UUID author);

     List<MultimediaFile> findAll();


}
