package com.works.api;


import com.works.api.error.AuthError;
import com.works.domain.Aluno;

import com.works.domain.JwtToken;
import com.works.domain.repository.AlunoRepository;
import com.works.filters.Authorize;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Path("alunos") // rota
public class AlunoResource {
	private final SecretKey secretKey = Keys.hmacShaKeyFor(
			"7f-j&CKk=coNzZc0y7_4obMP?#TfcYq%fcD0mDpenW2nc!lfGoZ|d?f&RNbDHUX6"
					.getBytes(StandardCharsets.UTF_8));


	// repositório
	private AlunoRepository alunoRepository = new AlunoRepository();

	// requisições
	@Authorize
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPessoas() {
		return Response.ok(alunoRepository.getAll()).build();
	}

	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(Aluno aluno){
		try{
			if((AlunoRepository.getByUsuario(aluno.getUsuario())) != null){
				if(aluno.getSenha().equals(AlunoRepository.getByUsuario(aluno.getUsuario()).getSenha()) ){
					String jwtToken = Jwts.builder()
							.setSubject(aluno.getUsuario())
							.setIssuer("localhost:8080")
							.setIssuedAt(new Date())
							.setExpiration(
									Date.from(
											LocalDateTime.now().plusMinutes(15L)
													.atZone(ZoneId.systemDefault())
													.toInstant()))
							.signWith(secretKey, SignatureAlgorithm.HS256)
							.compact();


					return Response.ok(new JwtToken(jwtToken)).build();

				}
				return Response.status(Response.Status.UNAUTHORIZED)
						.entity(new AuthError("Usuário e/ou senha inválidos")).build();


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

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createPessoa(Aluno atual) {
		try {
			alunoRepository.create(atual);
			return Response.status(Response.Status.CREATED)
					.entity(atual).build();
		} catch (Exception ex) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(ex.getMessage()).build();
		}
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPessoaById(@PathParam("id") int id) {
		try {
			if (alunoRepository.getById(id).equals(null)){
				return Response
						.status(Response.Status.NOT_FOUND).build();
			}
			return Response.ok(alunoRepository.getById(id)).build();
		} catch (Exception ex) {
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(ex.getMessage()).build();
		}
	}

	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editPessoa(@PathParam("id") int id, Aluno aluno)
	{
		Aluno p = alunoRepository.getById(id);
		if(p == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		try {
			aluno.setId(id);
			alunoRepository.edit(aluno);
			return Response.ok(aluno).build();
		} catch (Exception ex) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(ex.getMessage()).build();
		}
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deletePessoa(@PathParam("id") int id,
								 Aluno aluno) {
		Aluno p = alunoRepository.getById(id);
		if(p == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		try {
			alunoRepository.delete(id);
			return Response.noContent().build();
		} catch (Exception ex) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(ex.getMessage()).build();
		}
	}
}