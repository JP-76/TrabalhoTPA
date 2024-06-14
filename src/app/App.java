package app;

import lib.BinaryTreeImpl;
import lib.IArvoreBinaria;

public class App {

	public static void main(String[] args) {
		IArvoreBinaria<Aluno> arvAluno;
		IArvoreBinaria<Disciplina> arvDisciplina;

		AppService appService = new AppService();

		arvAluno = new BinaryTreeImpl<Aluno>(new ComparadorAlunoPorMatricula());
		arvDisciplina = new BinaryTreeImpl<Disciplina>(new ComparadorDisciplinaPorCodigo());

		String chosenOption;

		do {
			chosenOption = appService.showInitialMenu();

			switch (chosenOption) {
				case "1":
					appService.cadastrarAluno(arvAluno);
					break;
				case "2":
					appService.cadastrarDisciplina(arvDisciplina);
					break;
				case "3":
					appService.informarPreRequisito(arvDisciplina);
					break;
				case "4":
					appService.informarDisciplinaCursada(arvAluno, arvDisciplina);
					break;
				case "5":
					appService.consultarAlunoPorNome(arvAluno);
					break;
				case "6":
					appService.consultarAlunoPorMatricula(arvAluno);
					break;
				case "7":
					appService.excluirAlunoPorMatricula(arvAluno);
					break;
				case "8":
					break;
				default:
					System.out.println("Opção inválida");
			}

			// if (chosenOption.equals("1")) {
			// Aluno aluno = appService.cadastrarAluno(arvAluno);
			// arvAluno.adicionar(aluno);
			// System.out.println("Aluno cadastrado com sucesso!");
			// } else if (chosenOption.equals("2")) {
			// appService.cadastrarDisciplina(arvDisciplina);
			// } else if (chosenOption.equals("3")) {
			// appService.informarPreRequisito(arvDisciplina);
			// } else if (chosenOption.equals("4")) {
			// appService.informarDisciplinaCursada(arvAluno, arvDisciplina);
			// } else if (chosenOption.equals("5")) {
			// appService.consultarAlunoPorNome(arvAluno);
			// } else if (chosenOption.equals("6")) {
			// appService.consultarAlunoPorMatricula(arvAluno);
			// } else if (chosenOption.equals("7")) {
			// appService.excluirAlunoPorMatricula(arvAluno);
			// } else if (chosenOption.equals("8")) {
			// System.out.println("Saindo...");
			// } else {
			// System.out.println("Opção inválida");
			// }

		} while (!chosenOption.equals("8"));

	}
}
