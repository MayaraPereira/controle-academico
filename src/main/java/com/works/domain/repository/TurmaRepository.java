package com.works.domain.repository;

import com.works.domain.Turma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TurmaRepository {
	// banco de dados
	 protected static HashMap<Integer, Turma> turmas = new HashMap();

	public static HashMap<Integer, Turma> getTurmas() {
		return turmas;
	}

	public static void setTurmas(HashMap<Integer, Turma> turmas) {
		TurmaRepository.turmas = turmas;
	}

	// métodos
	public List<Turma> getAll() {
	 	return new ArrayList<Turma>(getTurmas().values());
	}
	
	public Turma getById(int id) {
		return getTurmas().get(id);
	}
	
	public void create(Turma turma){
		if(turma.getId() == 0) {
			turma.setId(genereteId(getTurmas().size() +1));
			getTurmas().put(turma.getId(), turma);
		}
	}

	private int genereteId(final int possible) {
		 if(getTurmas().containsKey(possible))
			 return genereteId(possible + 1);
		 return possible;
	}

	public void edit(Turma atual) {
		getTurmas().remove(atual.getId());
		getTurmas().put(atual.getId(), atual);
	}

	public void delete(int id) {
		getTurmas().remove(id);
	}
}

