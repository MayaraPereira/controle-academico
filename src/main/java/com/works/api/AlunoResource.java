package com.works.api;


import com.works.domain.Aluno;
import com.works.domain.repository.AlunoRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("alunos") // rota
public class AlunoResource {

	// repositório
	private AlunoRepository alunoRepository = new AlunoRepository();

	// requisições
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPessoas() {
		return Response.ok(alunoRepository.getAll()).build();
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