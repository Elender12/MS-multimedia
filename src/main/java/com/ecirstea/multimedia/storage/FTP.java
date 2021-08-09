package com.ecirstea.multimedia.storage;

import com.ecirstea.multimedia.exception.MultimediaException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * https://www.codejava.net/ftp-tutorials
 */
@Component
public class FTP implements Storage
{


    @Value("${storage.ftp.host}")
    private String host;

    @Value("${storage.ftp.port}")
    private int port;

    @Value("${storage.ftp.user}")
    private String user;

    @Value("${storage.ftp.pass}")
    private String pass;

    @Value("${storage.ftp.path}")
    private String path;

    private static final Logger logger = LogManager.getLogger(FTP.class);

    public FTP() { }



    @Override
    public byte[] read( UUID id )
    {
        byte[] resultData = new byte[0];

        FTPClient client = connect();
        try
        {
            InputStream inputStream = client.retrieveFileStream(path + id.toString());
            resultData = IOUtils.toByteArray(inputStream);
            inputStream.close();
        }
        catch( IOException ex )
        {
            logger.info("FTP read exception with " + id + " " + ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
            disconnect(client);
        }

        return resultData;
    }



    @Override
    public boolean write( UUID id, byte[] data )
    {
        FTPClient client = connect();
        try
        {
            InputStream inputStream = new ByteArrayInputStream(data);
            client.storeFile(path + id.toString(), inputStream);
            inputStream.close();
            return true;
        }
        catch( IOException ex )
        {
         logger.info("FTP write exception with " + id + " " + ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
            disconnect(client);
        }
        return false;
    }



    @Override
    public boolean delete( UUID id )
    {
        FTPClient client = connect();
        try
        {
            boolean deleted = client.deleteFile(path + id.toString());
            if( !deleted )
            {
                logger.info( "FTP: Could not delete the file " + id);
                return true;
            }
        }
        catch( Exception ex )
        {
            logger.info("FTP delete exception with " + id.toString() + " " + ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
            disconnect(client);
        }
        return false;
    }



    public boolean exists( UUID id )
    {
        FTPClient client = connect();
        try
        {
            return client.retrieveFileStream(path + id.toString()) != null && client.getReplyCode() != 550;
        }
        catch( IOException ex )
        {
            logger.info("FTP checkFileExists exception with " + id + " " + ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
            disconnect(client);
        }

        return false;
    }



    private FTPClient connect()
    {
        FTPClient client = new FTPClient();
        try
        {
            client.connect(host, port);

            if( !FTPReply.isPositiveCompletion(client.getReplyCode()) )
            {
                logger.info("FTP Connect failed.");
                throw new MultimediaException(HttpStatus.BAD_REQUEST, "FTP Connect failed");
            }

            if( !client.login(user, pass) )
            {
                logger.info("FTP Could not login to the server.");
                throw new MultimediaException(HttpStatus.BAD_REQUEST,"FTP Could not login to the server.");
            }

            client.enterLocalPassiveMode();
            client.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
        }
        catch( IOException ex )
        {
            logger.info("FTP there was an error: " + ex.getMessage());
            throw new MultimediaException(HttpStatus.BAD_REQUEST, "FTP there was an error: " + ex.getMessage());
        }

        return client;
    }



    private void disconnect( FTPClient client )
    {
        try
        {
            if( client.isConnected() )
            {
                client.logout();
                client.disconnect();
            }
        }
        catch( IOException ex )
        {
            logger.info("FTP Could not disconnect to the server.");
            throw new MultimediaException(HttpStatus.BAD_REQUEST, "FTP Could not disconnect to the server.");
        }
    }

}
