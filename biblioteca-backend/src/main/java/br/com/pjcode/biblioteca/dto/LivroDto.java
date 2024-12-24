package br.com.pjcode.biblioteca.dto;

import br.com.pjcode.biblioteca.constants.StatusLivroEnum;
import br.com.pjcode.biblioteca.domain.Livro;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LivroDto {

    private Long id;
    @NotBlank(message = "CDU não pode estar em branco e não pode ser nulo.")
    private String cdu;
    @NotBlank(message = "Título não pode estar em branco e não pode ser nulo.")
    private String titulo;
    @NotBlank(message = "Autor não pode estar em branco e não pode ser nulo.")
    private String autor;
    @NotBlank(message = "Editora não pode estar em branco e não pode ser nulo.")
    private String editora;
    private int quantidadeDisponivel;
    private String status  = StatusLivroEnum.DISPONIVEL.getDescricao();

    /**
     * Converte de Dto para Entity
     * @param dto
     * @return entity Livro
     */
    public static Livro toLivro(LivroDto dto) {
        return new Livro(
                dto.getId(),
                dto.getCdu(),
                dto.getTitulo(),
                dto.getAutor(),
                dto.getEditora(),
                dto.getQuantidadeDisponivel(),
                dto.getStatus()
        );
    }

    /**
     * Converte de Entity para Dto
     * @param entity
     * @return dto
     */
    public static LivroDto fromLivro(Livro entity){
        return new LivroDto(
                entity.getId(),
                entity.getCdu(),
                entity.getTitulo(),
                entity.getAutor(),
                entity.getEditora(),
                entity.getQuantidadeDisponivel(),
                entity.getStatus()
        );
    }

    /**
     * Converte uma lista de Dtos para uma lista de Entiy
     * @param livros
     * @return lista de livros
     */
    public static List<Livro> toConvertList(List<LivroDto> livros) {
        return livros.stream()
                .map((LivroDto::toLivro))
                .collect(Collectors.toList());
    }

    /**
     * Converte uma lista de Entiy para uma lista de Dtos
     * @param livros
     * @return lista de livros
     */
    public static List<LivroDto> fromConvertList(List<Livro> livros) {
        return livros.stream()
                .map((LivroDto::fromLivro))
                .collect(Collectors.toList());
    }

}
