package app;

import java.util.Scanner;
import lib.IArvoreBinaria;

public class AppService {

	/*
	nextInt está dando problema, uma solução do stackoverflow é usar o nextLine depois.
	FONTE: https://stackoverflow.com/questions/5032356/using-scanner-nextline
	Achamos que este problema é devido estarmos utilizando uma versão antiga do Java,
	pois em versões mais recentes não ocorre este problema.
	*/
	Scanner sc = new Scanner(System.in);

	public AppService() {}

	public String showInitialMenu() {

		System.out.println("1 - Cadastrar Aluno");
		System.out.println("2 - Cadastrar Disciplina");
		System.out.println("3 - Informar pré-requisito");
		System.out.println("4 - Informar Disciplina cursada");
		System.out.println("5 - Consultar Aluno por Nome");
		System.out.println("6 - Consultar Aluno por Matrícula");
		System.out.println("7 - Excluir Aluno por Matrícula");
		System.out.println("8 - Sair do sistema");

		String chosenOption = sc.nextLine();

		return chosenOption;
	}

	public void cadastrarAluno(IArvoreBinaria<Aluno> arvAluno) {
		
		System.out.println("Digite a matrícula do aluno: ");
		int matricula = sc.nextInt();
		sc.nextLine();
		System.out.println("Digite o nome do aluno: ");
		String nome = sc.nextLine();

		arvAluno.adicionar(new Aluno(matricula, nome));
		System.out.println("Aluno cadastrado com sucesso!");
	}

	public void cadastrarDisciplina(IArvoreBinaria<Disciplina> arvDisciplina) {

		System.out.println("Digite o código da disciplina: ");
		int codigo = sc.nextInt();
		sc.nextLine();
		System.out.println("Digite o nome da disciplina: ");
		String nome = sc.nextLine();
		System.out.println("Digite a carga horária da disciplina: ");
		int cargaHoraria = sc.nextInt();
		sc.nextLine();

		arvDisciplina.adicionar(new Disciplina(codigo, nome, cargaHoraria));
		System.out.println("Disciplina cadastrada com sucesso!");
	}

	public void informarPreRequisito(IArvoreBinaria<Disciplina> arvDisciplina) {

		System.out.println("Digite o código da disciplina que será pré-requisito: ");
		int codigo = sc.nextInt();
		sc.nextLine();
		Disciplina disciplina = arvDisciplina.pesquisar(new Disciplina(codigo, null, 0));

		System.out.println("Digite o código da disciplina que terá a anterior como pré-requisito: ");
		int codigoPreReq = sc.nextInt();
		sc.nextLine();
		Disciplina preReq = arvDisciplina.pesquisar(new Disciplina(codigoPreReq, null, 0));

		for (Disciplina elem : disciplina.getPreReq()) {
			if (elem == disciplina) {
				System.out.println("Disciplina informada já esta cadastrada como pré-requisito");
				return;
			}
		}

		disciplina.addPreReq(preReq); 
		System.out.println("Pré-requisito informado com sucesso!");
	}

	public void informarDisciplinaCursada(IArvoreBinaria<Aluno> arvAluno, IArvoreBinaria<Disciplina> arvDisciplina) {

		System.out.println("Digite a matrícula do aluno a ter disciplina cursada: ");
		int matricula = sc.nextInt();
		sc.nextLine();

		Aluno aluno = arvAluno.pesquisar(new Aluno(matricula, null));

		if (aluno == null) {
			System.out.println("Aluno não encontrado!");
			sc.close();
			return;
		}

		System.out.println("Digite o código da disciplina cursada: ");
		int codigo = sc.nextInt();
		sc.nextLine();

		Disciplina disciplinaCursada = arvDisciplina.pesquisar(new Disciplina(codigo, null, 0));

		if (disciplinaCursada == null) {
			System.out.println("Disciplina não encontrada!");
			return;
		}

		for (Disciplina elem : aluno.getDisciplinasCursadas()) {
			if (elem.getCodigo() == codigo) {
				System.out.println("Disciplina já cursada!");
				return;
			}
		}

		aluno.addDisciplinaCursada(disciplinaCursada);
		System.out.println("Disciplina cursada informada com sucesso!");
	}

	public void consultarAlunoPorNome(IArvoreBinaria<Aluno> arvAluno) {

		System.out.println("Digite o nome do aluno: ");
		String nome = sc.nextLine();

		Aluno aluno = arvAluno.pesquisar(new Aluno(0, nome), new ComparadorAlunoPorNome());

		if (aluno != null) {
			System.out.println("Informações do aluno: ");
			System.out.println(aluno);
		} else {
			System.out.println("Aluno não encontrado!");
		}
	}

	public void consultarAlunoPorMatricula(IArvoreBinaria<Aluno> arvAluno) {

		System.out.println("Digite a matrícula do aluno: ");
		int matricula = sc.nextInt();
		sc.nextLine();

		Aluno aluno = arvAluno.pesquisar(new Aluno(matricula, ""), new ComparadorAlunoPorMatricula());

		if (aluno != null) {
			System.out.println("Informações do aluno: ");
			System.out.println(aluno);
		} else {
			System.out.println("Aluno não encontrado!");
		}
	}

	public void excluirAlunoPorMatricula(IArvoreBinaria<Aluno> arvAluno) {
		// TODO: Fazer o código de exclusão aqui, levar como base os outros métodos acima
	}
}
