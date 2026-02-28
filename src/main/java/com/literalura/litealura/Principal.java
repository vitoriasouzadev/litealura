package com.literalura.litealura;

import java.util.Scanner;

import com.literalura.litealura.dto.DadosLivro;
import com.literalura.litealura.dto.RespostaApi;
import com.literalura.litealura.model.Autor;
import com.literalura.litealura.model.Livro;
import com.literalura.litealura.repository.AutorRepository;
import com.literalura.litealura.repository.LivroRepository;
import com.literalura.litealura.service.ConsumoApi;
import com.literalura.litealura.service.ConverterDados;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Principal implements CommandLineRunner {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    public Principal(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    @Override
    public void run(String... args) {

        Scanner leitura = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {

            System.out.println("""
                    
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros por idioma
                    0 - Sair
                    """);

            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {

                case 1:
                    System.out.println("Digite o nome do livro:");
                    String titulo = leitura.nextLine();
                    buscarLivro(titulo);
                    break;

                case 2:
                    livroRepository.findAll()
                            .forEach(System.out::println);
                    break;

                case 3:
                    autorRepository.findAll()
                            .forEach(System.out::println);
                    break;

                case 4:
                    System.out.println("Digite o ano:");
                    int ano = leitura.nextInt();
                    leitura.nextLine();

                    autorRepository.findAll().stream()
                            .filter(a -> a.getAnoNascimento() != null &&
                                    a.getAnoNascimento() <= ano &&
                                    (a.getAnoFalecimento() == null || a.getAnoFalecimento() >= ano))
                            .forEach(System.out::println);
                    break;

                case 5:
                    System.out.println("Digite o idioma (ex: en, pt):");
                    String idioma = leitura.nextLine();

                    livroRepository.findAll().stream()
                            .filter(l -> l.getIdioma().equalsIgnoreCase(idioma))
                            .forEach(System.out::println);
                    break;

                case 0:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivro(String titulo) {

        ConsumoApi consumo = new ConsumoApi();
        ConverterDados conversor = new ConverterDados();

        String endereco = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");

        String json = consumo.obterDados(endereco);
        RespostaApi dados = conversor.obterDados(json, RespostaApi.class);

        if (!dados.results().isEmpty()) {

            DadosLivro dadosLivro = dados.results().get(0);

            Autor autor = new Autor();
            autor.setNome(dadosLivro.authors().get(0).name());
            autor.setAnoNascimento(dadosLivro.authors().get(0).birth_year());
            autor.setAnoFalecimento(dadosLivro.authors().get(0).death_year());

            autorRepository.save(autor);

            Livro livro = new Livro();
            livro.setTitulo(dadosLivro.title());
            livro.setIdioma(dadosLivro.languages().get(0));
            livro.setNumeroDownloads(dadosLivro.download_count());
            livro.setAutor(autor);

            livroRepository.save(livro);

            System.out.println("Livro salvo com sucesso!");

        } else {
            System.out.println("Livro não encontrado.");
        }
    }
}