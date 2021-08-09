package com.ecirstea.multimedia.service;



import com.ecirstea.multimedia.exception.MultimediaException;
import com.ecirstea.multimedia.model.MultimediaFile;
import com.ecirstea.multimedia.repository.MultimediaRepositoryImpl;
import com.ecirstea.multimedia.storage.FTP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class MultimediaApiServiceImpl implements MultimediaApiService {

    private static final Logger logger = LogManager.getLogger(MultimediaApiServiceImpl.class);

    @Autowired
    private final MultimediaRepositoryImpl multimediaRepository;



    public MultimediaApiServiceImpl(MultimediaRepositoryImpl mongoDBRepository )
    {
        this.multimediaRepository = mongoDBRepository;
    }

    @Override
    public MultimediaFile save(MultimediaFile multimedia )
    {
        if( multimedia.getId() == null )
        {
            multimedia.makeId();
            multimedia.setModified(null);
            multimedia.setCreated(new Date());
        }
        else
        {
            multimedia.setModified(new Date());

            MultimediaFile temp = this.findById(multimedia.getId());
            multimedia.setDownloadCounter(temp.getDownloadCounter());
            multimedia.setCreated(temp.getCreated());
        }
        MultimediaFile temp = this.multimediaRepository.save(multimedia);
        logger.info("Saved multimedia with id: " + multimedia.getId());
        return this.multimediaRepository.save(multimedia);
    }



    @Override
    public MultimediaFile edit( MultimediaFile multimedia, boolean throwExceptionIfNotExist )
    {
        if( !throwExceptionIfNotExist )
        {
            try
            {
                MultimediaFile temp = this.findById(multimedia.getId());
                multimedia.setCreated(temp.getCreated());
                multimedia.setModified(new Date());
            }
            catch( MultimediaException ex )
            {
                multimedia.setCreated(new Date());
            }
        }
        else
        {
            MultimediaFile temp = this.findById(multimedia.getId()); //throw exception if it doesn't exist.
            multimedia.setCreated(temp.getCreated());
            multimedia.setModified(new Date());
        }

        logger.info("Edited multimedia with id: " + multimedia.getId());
        return this.multimediaRepository.save(multimedia);
    }



    @Override
    public MultimediaFile delete( UUID id )
    {
        MultimediaFile multimedia = this.multimediaRepository.findById(id);

        if( multimedia != null )
        {
            this.multimediaRepository.deleteById(multimedia.getId());
           logger.info("Deleted multimedia with id: " + multimedia.getId());
            return multimedia;
        }
        else
        {
            logger.info("Multimedia not found with id " + id);
            throw new MultimediaException(HttpStatus.NOT_FOUND, "id");
        }
    }



    @Override
    public MultimediaFile findById( UUID id )
    {
        MultimediaFile multimedia = this.multimediaRepository.findById(id);

        if( multimedia != null )
        {
            logger.info("Found multimedia with id: " + multimedia.getId());
            return multimedia;
        }
        else
        {
            logger.info("Multimedia not found with id " + id);
            throw new MultimediaException(HttpStatus.NOT_FOUND, "id");
        }
    }



    @Override
    public List<MultimediaFile> findAll()
    {
        List<MultimediaFile> multimedias = new ArrayList<>(this.multimediaRepository.findAll());
        logger.info("Returned all multimedias.");
        return multimedias;
    }
}
