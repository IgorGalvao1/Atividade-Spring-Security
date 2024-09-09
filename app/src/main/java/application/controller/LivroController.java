package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.model.Livro;
import application.repository.LivroRepository;

@RestController
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroRepository livroRepo;

    @GetMapping
    public Iterable<Livro> getAll() {
        return livroRepo.findAll();
    }

    @PostMapping
    public Livro post(@RequestBody Livro livro) {
        return livroRepo.save(livro);
    }

    @GetMapping("/{id}")
    public Livro getOne(@PathVariable long id) {
        Optional<Livro> resultado = livroRepo.findById(id);
        if(resultado.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Livro não encontrado"
            );
        }
        return resultado.get();
    }

    @PutMapping("/{id}")
    public Livro put(@PathVariable long id, @RequestBody Livro novosDados){
        Optional<Livro> resultado = livroRepo.findById(id);

        if(resultado.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Livro não encontrado"
            );
        }

        Livro livro = resultado.get();
        livro.setTitulo(novosDados.getTitulo());
        livro.setAnoPublicacao(novosDados.getAnoPublicacao());

        return livroRepo.save(livro);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        if(!livroRepo.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Livro não encontrado"
            );
        }

        livroRepo.deleteById(id);
    }
}
