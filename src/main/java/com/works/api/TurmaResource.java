package com.works.api;


import com.works.domain.Turma;
import com.works.domain.repository.TurmaRepository;
import com.works.filters.Authorize;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("turmas") // rota
public class TurmaResource {

	// repositório
	private TurmaRepository turmaRepository = new TurmaRepository();


	// requisições
	@Authorize
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTurmas() {
		return Response.ok(turmaRepository.getAll()).build();
}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTurma(Turma p) {
		try {
			turmaRepository.create(p);
			return Response.status(Response.Status.CREATED)
					.entity(p).build();
		} catch (Exception ex) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(ex.getMessage()).build();
		}
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTurmaById(@PathParam("id") int id) {
		try {
			if (turmaRepository.getById(id).equals(null)){
				return Response
						.status(Response.Status.NOT_FOUND).build();
			}
			return Response.ok(turmaRepository.getById(id)).build();
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
	public Response editTurma(@PathParam("id") int id, Turma turma)
	{
		Turma atual = turmaRepository.getById(id);
		if(atual == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		try {
			turma.setId(id);
			turmaRepository.edit(turma);
			return Response.ok(turma).build();
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
								 Turma turma) {
		Turma p = turmaRepository.getById(id);
		if(turma == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		try {
			turmaRepository.delete(id);
			return Response.noContent().build();
		} catch (Exception ex) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(ex.getMessage()).build();
		}
	}
}