package com.works.domain.repository;

import com.works.domain.Aluno;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AlunoRepository {
	// banco de dados
	 protected static HashMap<Integer, Aluno> alunos = new HashMap<>();

	public static HashMap<Integer, Aluno> getAlunos() {
		return alunos;
	}

	public static void setAlunos(HashMap<Integer, Aluno> alunos) {
		AlunoRepository.alunos = alunos;
	}

	// métodos
	public List<Aluno> getAll() {
	 	return new ArrayList<Aluno>(getAlunos().values());
	}

	public static Aluno getById(int id) {
		return getAlunos().get(id);
	}

	public static Aluno getByUsuario(String usuario) {
		Iterator<Aluno> it = alunos.values().iterator();

		while(it.hasNext()){
			Aluno al = it.next();
			if (al.getUsuario().equals(usuario)){
				return al;
			}

		}
		return null;
	}

	public void create(Aluno aluno){
		if(aluno.getId() == 0) {
			aluno.setId(genereteId(getAlunos().size() +1));
			getAlunos().put(aluno.getId(), aluno);
		}
	}

	private int genereteId(final int possible) {
		 if(getAlunos().containsKey(possible))
			 return genereteId(possible + 1);
		 return possible;
	}

	public void edit(Aluno actual) {
		if (actual == null ) {
			// consider wrapping this with a ConstraintViolationException if
			// Bean Validation is used.
			throw new IllegalArgumentException("id must not be null");
		}
		getAlunos().remove(actual.getId());
		getAlunos().put(actual.getId(), actual);
	}

	public void delete(int id) {
		getAlunos().remove(id);
	}
}

