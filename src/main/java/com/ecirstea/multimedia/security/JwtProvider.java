package com.ecirstea.multimedia.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
class JwtProvider
{
    @Value("${jwt.secret}")
    private String secret;

    public String getUserNameFromJwtToken( String token )
    {
        return Jwts.parser()
            .setSigningKey(secret.getBytes())
            .parseClaimsJws(token)
            .getBody().getSubject();
    }

    public String getPasswordFromJwtToken( String token )
    {
        return Jwts.parser()
            .setSigningKey(secret.getBytes())
            .parseClaimsJws(token)
            .getBody().get("password").toString();
    }

    public boolean validateJwtToken( String authToken )
    {
        try
        {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(authToken).getBody();
            return true;
        }
        catch( MalformedJwtException | IllegalArgumentException | UnsupportedJwtException | ExpiredJwtException e )
        {
            e.printStackTrace();
        }
        return false;
    }

}