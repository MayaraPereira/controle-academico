package com.works.api;

import com.works.api.error.AuthError;
import com.works.domain.Aluno;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Path("/login")
public class LoginResource {
    private final SecretKey CHAVE = Keys.hmacShaKeyFor(
            "7f-j&CKk=coNzZc0y7_4obMP?#TfcYq%fcD0mDpenW2nc!lfGoZ|d?f&RNbDHUX6"
                    .getBytes(StandardCharsets.UTF_8));

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(Aluno aluno)
    {
        try{
            if(
                    aluno.getNome().equals("teste@treinaweb.com.br")
                            &&
                            aluno.getSenha().equals("1234")
            )
            {
                String jwtToken = Jwts.builder()
                        .setSubject(aluno.getNome())
                        .setIssuer("localhost:8080")
                        .setIssuedAt(new Date())
                        .setExpiration(
                                Date.from(
                                        LocalDateTime.now().plusMinutes(15L)
                                                .atZone(ZoneId.systemDefault())
                                                .toInstant()))
                        .signWith(CHAVE, SignatureAlgorithm.RS512)
                        .compact();

                return Response.status(Response.Status.OK).entity(jwtToken).build();
            }
            else
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new AuthError("Usuário e/ou senha inválidos")).build();
        }
        catch(Exception ex)
        {
            return Response.status(
                    Response.Status.INTERNAL_SERVER_ERROR
            ).entity(ex.getMessage())
                    .build();
        }
    }
}
