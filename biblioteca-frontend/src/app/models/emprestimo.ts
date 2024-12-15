import { Leitor } from "./leitor-model";
import { Livro } from "./livro-model";

export class Emprestimo {
  id?: number;
  leitor: Leitor;
  livros: Livro[] = [];
  dataEmprestimo?: string;
  dataDevolucaoPrevista?: string;
  dataDevolucaoReal?: string;

  constructor(
    leitor: Leitor,
    livros: Livro[],
    dataEmprestimo?: string,
    dataDevolucaoPrevista?: string,
    dataDevolucaoReal?: string
  )
  {
    this.leitor = leitor;
    this.livros = livros;
    this.dataEmprestimo = dataEmprestimo;
    this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    this.dataDevolucaoReal = dataDevolucaoReal;
  }

  getLivrosFormatados(): string {
    return this.livros.map(livro => livro.titulo).join("\n");
  }
}
