export class Leitor {
  id?: number;
  nome?: string;
  cpf?: string;
  rg?: string;
  dataNascimento?: string;
  telefone?: string;
  profissao?: string;
  escola?: string;
  serie?: string;
  curso?: string;
  turno?: string;
  endereco: any;

  /**
   * Cria um novo Leitor.
   * @param id O ID do Leitor, se for um Leitor existente.
   * @param nome O nome do Leitor.
   * @param cpf O CPF do Leitor.
   * @param rg O RG do Leitor.
   * @param dataNascimento A data de nascimento do Leitor.
   * @param telefone O telefone do Leitor.
   * @param profissao A profiss o do Leitor.
   * @param escola A escola do Leitor.
   * @param serie A série do Leitor.
   * @param curso O curso do Leitor.
   * @param turno O turno do Leitor.
   */
  constructor (
    id?: number,
    nome?: string,
    cpf?: string,
    rg?: string,
    dataNascimento?: string,
    telefone?: string,
    profissao?: string,
    escola?: string,
    serie?: string,
    curso?: string,
    turno?: string,
    endereco?: any
  )

  {
    this.id = id;
    this.nome = nome;
    this.cpf = cpf;
    this.rg = rg;
    this.dataNascimento = dataNascimento;
    this.telefone = telefone;
    this.profissao = profissao;
    this.escola = escola;
    this.serie = serie;
    this.curso = curso;
    this.turno = turno;
    this.endereco = endereco;
  }
}
