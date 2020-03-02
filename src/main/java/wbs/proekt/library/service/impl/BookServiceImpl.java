package wbs.proekt.library.service.impl;

import org.apache.jena.query.*;
import org.springframework.stereotype.Service;
import wbs.proekt.library.models.Author;
import wbs.proekt.library.models.Book;
import wbs.proekt.library.repository.BookRepository;
import wbs.proekt.library.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByBookName(String name) {
        return bookRepository.findByBookName(name);
    }

    @Override
    public Book getBookDetailsSPARQL(String booklink) {

        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        String query =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                        "PREFIX dbo: <http://dbpedia.org/ontology/> " +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "SELECT distinct ?authorName ?authorLink ?bookLink ?bookName ?bookAbstract ?numberOfPages ?comment WHERE { " +
                            "?bookLink rdf:type dbo:Book; " +
                            "rdfs:label ?bookName; " +
                            "dbo:abstract ?bookAbstract; " +
                            "rdfs:comment ?comment; " +
                            "dbo:author ?authorLink. " +
                            "?authorLink rdfs:label ?authorName. " +
                            "OPTIONAL {?bookLink dbo:numberOfPages ?numberOfPages.}" +
                            "FILTER(regex (str(?bookLink), '" + booklink + "' , 'i') ). " +
                            "FILTER(lang(?bookName) = 'en') " +
                            "FILTER(lang(?bookAbstract) = 'en') " +
                            "FILTER(lang(?comment) = 'en') " +
                            "}";

        Book book = new Book();
        Query sparqlQuery = QueryFactory.create(query);
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                // ?authorLink ?bookLink ?bookName ?bookAbstract  ?numberOfPages ?comment
                QuerySolution solution = resultSet.nextSolution();
                String authorName = solution.get("authorName").toString().substring(0,solution.get("authorName").toString().length()-3);
                String authorLink = solution.get("authorLink").toString();
                String bookLink = solution.get("bookLink").toString();
                String bookName = solution.get("bookName").toString().substring(0,solution.get("bookName").toString().length()-3);
                String bookAbstract = solution.get("bookAbstract").toString().substring(0,solution.get("bookAbstract").toString().length()-3);

                String numberOfPages = solution.get("?numberOfPages").toString().substring(0,solution.get("numberOfPages").toString().length()-50);
                String comment = solution.get("comment").toString().substring(0,solution.get("comment").toString().length()-3);

                book.setAuthorName(authorName);
                book.setAuthorLink(authorLink);
                book.setBookLink(bookLink);
                book.setBookName(bookName);
                book.setBookAbstract(bookAbstract);
                book.setNumberOfPages(numberOfPages);
                book.setComment(comment);
            }
        }

        return book;



    }

    @Override
    public Book getBookDetailsNAME(String bookname) {

        System.out.println(bookname);
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        String query =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                        "PREFIX dbo: <http://dbpedia.org/ontology/> " +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "SELECT distinct ?authorName ?authorLink ?bookLink ?bookName ?bookAbstract ?numberOfPages ?comment WHERE { " +
                        "?bookLink rdf:type dbo:Book; " +
                        "rdfs:label ?bookName; " +
                        //"rdfs:label '" + bookname + "'; " +
                        "dbo:abstract ?bookAbstract; " +
                        "rdfs:comment ?comment; " +
                        "dbo:author ?authorLink. " +
                        "?authorLink rdfs:label ?authorName. " +

                        "OPTIONAL {?bookLink dbo:numberOfPages ?numberOfPages.}" +

                        "FILTER(lang(?bookName) = 'en') " +
                        "FILTER(regex ( '" + bookname + "' , str(?bookName),  'i') ). " +

                        "FILTER(lang(?bookAbstract) = 'en') " +
                        "FILTER(lang(?comment) = 'en') " +
                        "}" + "LIMIT 1";

        Book book = new Book();
        Query sparqlQuery = QueryFactory.create(query);
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                // ?authorLink ?bookLink ?bookName ?bookAbstract  ?numberOfPages ?comment
                QuerySolution solution = resultSet.nextSolution();
                String authorName = solution.get("authorName").toString().substring(0,solution.get("authorName").toString().length()-3);
                String authorLink = solution.get("authorLink").toString();
                String bookLink = solution.get("bookLink").toString();
                String bookName = solution.get("bookName").toString().substring(0,solution.get("bookName").toString().length()-3);
                String bookAbstract = solution.get("bookAbstract").toString().substring(0,solution.get("bookAbstract").toString().length()-3);

                String numberOfPages = "";
                if (solution.get("?numberOfPages").equals(null)){
                    numberOfPages = "";
                }
                else{
                    numberOfPages = solution.get("?numberOfPages").toString().substring(0,solution.get("numberOfPages").toString().length()-50);
                }
                String comment = solution.get("comment").toString().substring(0,solution.get("comment").toString().length()-3);

                book.setAuthorName(authorName);
                book.setAuthorLink(authorLink);
                book.setBookLink(bookLink);
                book.setBookName(bookName);
                book.setBookAbstract(bookAbstract);
                book.setNumberOfPages(numberOfPages);
                book.setComment(comment);
            }
        }

        return book;


    }
}
