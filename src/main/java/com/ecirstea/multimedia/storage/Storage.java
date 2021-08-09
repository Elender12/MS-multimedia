package com.ecirstea.multimedia.storage;


import java.util.UUID;


public interface Storage
{

    byte[] read( UUID id );


    boolean write( UUID id, byte[] data );


    boolean delete( UUID id );


    boolean exists( UUID id );

}
