package com.works.domain;
import com.works.domain.repository.AlunoRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Turma {
        private int id;
        private String nome;
        private String turno;
        private List<Aluno> alunosMatriculados = new ArrayList<Aluno>();

        public List<Aluno> getalunosMatriculados() { //retorna todos os alunos da turma
                return new ArrayList<Aluno>(alunosMatriculados);
        }

        public Aluno getAlunoDaTurma(int id) { //retorna apenas 1 aluno especifico da turma
                return alunosMatriculados.get(id);
        }

        public void realizarMatricula(int idAluno){
                Aluno aluno = AlunoRepository.getAlunos().get(idAluno);
                alunosMatriculados.add(aluno);

        }
}

