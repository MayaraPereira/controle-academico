package com.works.api;

import com.works.domain.Aluno;
import com.works.domain.RegistroMatricula;
import com.works.domain.Turma;
import com.works.domain.repository.AlunoRepository;
import com.works.domain.repository.TurmaRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("matriculas")
public class RegistroMatriculaResource {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response matricula( RegistroMatricula matricula){

        Turma turma = TurmaRepository.getTurmas().get(matricula.getIdTurma());
        Aluno aluno = AlunoRepository.getAlunos().get(matricula.getIdAluno());

        if(turma == null || aluno == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        try {
            turma.realizarMatricula(aluno.getId());
            return Response.status(Response.Status.CREATED).build();

        } catch (Exception ex) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getMessage()).build();
        }
    }
}

