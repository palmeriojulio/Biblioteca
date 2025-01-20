package br.com.pjcode.biblioteca.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.pjcode.biblioteca.dto.LivrosMaisEmprestadosDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pjcode.biblioteca.dao.LivroRepository;
import br.com.pjcode.biblioteca.domain.Livro;
import br.com.pjcode.biblioteca.dto.LivroDto;
import br.com.pjcode.biblioteca.service.exceptions.ConflictException;
import br.com.pjcode.biblioteca.service.exceptions.InternalServerErrorException;
import br.com.pjcode.biblioteca.service.exceptions.ResourceNotFoundException;

/**
 * @author Palmério Júlio
 * Classe serviço com o CRUD da entidade livro, trata regras de negócio, exeções e se comunica com o repositório.
 */
@Service
public class LivroService {

    @Autowired
    LivroRepository livroRepository;

    /**
     * Método para salvar um livro no banco.
     * @author Palmério Júlio
     * @param livroDto
     * @return Object com o Livro cadastrado.
     * @throws ConflictException
     * @exception InternalServerErrorException
     */
    @Transactional
    public Object save(LivroDto livroDto) throws ConflictException {
        try {
            Boolean retorno = livroRepository.existsByTitulo(livroDto.getTitulo());
            if (retorno){
                throw new ConflictException("Já existe um livro com o titulo: "+livroDto.getTitulo());
            }
            var livro = livroRepository.save(LivroDto.toLivro(livroDto));
            return convertReturn(livro);
        } catch (ConflictException e) {
            throw e;
        } catch (Exception e) {
            return new InternalServerErrorException("Erro ao cadastrar o livro, entre em contato com o suporte");
        }
    }

    /**
     * Método para buscar todos os registros de livro já cadastrados.
     * @author Palmério Júlio
     * @return List<Livro>
     * @exception InternalServerErrorException
     */
    @Transactional(readOnly = true)
    public List<LivroDto> getAll() {
        try {
            return livroRepository.findAll()
                    .stream()
                    .map(LivroDto::fromLivro)
                    .sorted((l1, l2) -> l1.getId().compareTo(l2.getId()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar os livros");
        }
    }

    /**
     * Método para buscar todos os registros de livro já cadastrados que possuem ao menos uma unidade disponível.
     * @author Palmério Júlio
     * @return List<Livro>
     * @exception InternalServerErrorException
     */
    @Transactional(readOnly = true)
    public List<LivroDto> findAllByDisponiveis() {
        try {
            return livroRepository.findAll()
                    .stream()
                    .filter(l -> l.getQuantidadeDisponivel() > 0)
                    .map(LivroDto::fromLivro)
                    .sorted((l1, l2) -> l1.getId().compareTo(l2.getId()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar os livros");
        }
    }

    /**
     * Método para buscar um livro por ID já cadastrado.
     * @param id
     * @return Object com o Livro referente ao "ID" passado como parâmetro.
     * @throws ResourceNotFoundException
     * @exception InternalServerErrorException
     */
    @Transactional(readOnly = true)
    public Object findById(Long id) {
        try {
            var livro = livroRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Livro com id: "+id+" não encontrado!"));
            return convertReturn(livro);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new InternalServerErrorException("Erro ao buscar o livro!");
        }
    }

    /**
     * Método para buscar um livro por CDU já cadastrado.
     * @param cdu
     * @return Object com o Livro referente ao "CDU" passado como parâmetro.
     * @throws ResourceNotFoundException
     * @exception InternalServerErrorException
     */
    @Transactional(readOnly = true)
    public Object findByCdu(String cdu) {
        try {
            var livro = livroRepository.findByCdu(cdu).
                    orElseThrow(() -> new ResourceNotFoundException("Livro com CDU: "+cdu+" não encontrado"));
            return convertReturn(livro);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new InternalServerErrorException("Erro ao buscar o livro!");
        }
    }

    /**
     * Método para buscar um livro por Título já cadastrado.
     * @param titulo
     * @return Object com o Livro referente ao "Título" passado como parâmetro.
     * @throws ResourceNotFoundException
     * @exception InternalServerErrorException
     */
    @Transactional(readOnly = true)
    public Object findByTitulo(String titulo) {
        try {
            var livro = livroRepository.findByTitulo(titulo).
                    orElseThrow(() -> new ResourceNotFoundException("Livro com titulo: "+titulo+" não encontrado"));
            return convertReturn(livro);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new InternalServerErrorException("Erro ao buscar o livro!");
        }
    }

    /**
     * Método para atualizar os dados de um livro.
     * @author Palmério Júlio
     * @param livroDto
     * @param id
     * @return Entity de Livro atualizado.
     * @throws ResourceNotFoundException
     * @exception InternalServerErrorException
     */
    @Transactional
    public Object update(LivroDto livroDto, Long id) {
        try {
            var livro = livroRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Livro com id: "+id+" não encontrado!"));
            BeanUtils.copyProperties(livroDto, livro, "id");
            livro = livroRepository.save(livro);
            return convertReturn(livro);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new InternalServerErrorException("Erro ao atualizar o livro, ");
        }
    }

    /**
     * Método para deletar um livro já cadastrado.
     * @param id
     * @return Object, com uma mensagem caso o livro tenho sido deletado.
     * @throws ResourceNotFoundException
     * @exception InternalServerErrorException
     */
    @Transactional
    public Object delete(Long id) {
        try {
            if (livroRepository.findById(id).isEmpty()) {
                throw new ResourceNotFoundException("Livro com id: "+id+" não encontrado!");
            }
            livroRepository.deleteById(id);
            return "Livro excluído com sucesso!";
        } catch (ResourceNotFoundException e) {
            return e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao deletar o livro");
        }
    }

    public Long countAllLivros() {
        return livroRepository.countAllLivros();
    }

    public List<LivrosMaisEmprestadosDto> livrosMaisEmprestados() {
        PageRequest pageRequest = PageRequest.of(0,5);
        return livroRepository.livrosMaisEmprestados(pageRequest);
    }

    /**
     * Método que para converter Entity em DTO.
     * @param livro Optional.
     * @return Dto de livro.
     */
    private LivroDto convertOptionalReturn(Optional<Livro> livro) {
        try {
            if (livro.isPresent()){
                return LivroDto.fromLivro(livro.get());
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método que para converter Entity em DTO.
     * @param livro
     * @return Dto de livro
     */
    private LivroDto convertReturn(Livro livro) {
        try {
            if (Objects.nonNull(livro)) {
                return LivroDto.fromLivro(livro);
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
