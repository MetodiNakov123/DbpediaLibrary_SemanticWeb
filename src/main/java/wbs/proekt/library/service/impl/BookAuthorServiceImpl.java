package wbs.proekt.library.service.impl;

import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Lists;
import wbs.proekt.library.models.BookAuthor;
import wbs.proekt.library.repository.BookAuthorRepository;
import wbs.proekt.library.service.BookAuthorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.jena.query.*;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {

    private BookAuthorRepository bookAuthorRepository;

    public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
    }

    @Override
    public List<BookAuthor> findAll() {
        return bookAuthorRepository.findAll();
    }

    @Override
    public BookAuthor save(BookAuthor bookAuthor) {
        return bookAuthorRepository.save(bookAuthor);
    }

    @Override
    public Optional<BookAuthor> findById(Long id) {
        return bookAuthorRepository.findById(id);
    }

    @Override
    public Optional<BookAuthor> findByBookName(String bookName) {
        return bookAuthorRepository.findByBookName(bookName);
    }

    @Override
    public Optional<BookAuthor> findByAuthorName(String authorName) {
        return bookAuthorRepository.findByAuthorName(authorName);
    }

    @Override
    public List<BookAuthor> getAllSPARQL() {

        List<BookAuthor> list = new ArrayList<>();
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX dbo: <http://dbpedia.org/ontology/> " +

                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +

                "SELECT distinct ?s ?bookName ?authorLink ?author  WHERE { " +
                "?s rdf:type dbo:Book; " +
                "rdfs:label ?bookName; " +


                "dbo:abstract ?bookAbstract; " +
                "rdfs:comment ?comment; " +
                "dbo:numberOfPages ?numberOfPages; " +



                "dbo:author ?authorLink. " +
//"OPTIONAL {?bookLink dbo:numberOfPages ?numberOfPages.}" +





                "?authorLink rdfs:label ?author; " +



                "a dbo:Person; " +
                "dbo:abstract ?Abstract; " +
                "dbo:birthDate ?birthDate; " +
                "dbo:deathDate ?deathDate; " +
                "dbo:birthPlace ?placeOfBirthLink. " +
                "?placeOfBirthLink rdfs:label ?PlaceOfBirth; " +

                "geo:lat ?latitude; " +
                "geo:long ?longitude. " +





              //"?authorLink dbo:birthDate ?birthDate; " +
                "FILTER (lang(?author) = 'en') " +
                "FILTER (lang(?bookName) = 'en') " +
                "FILTER(lang(?Abstract) = 'en') " +
                "FILTER(lang(?PlaceOfBirth) = 'en') " +
                "FILTER(lang(?bookAbstract) = 'en') " +
                "FILTER(lang(?comment) = 'en') " +
                "} " +
                // "ORDER BY DESC(?birthDate)"+
                "LIMIT 51" ;


        Query sparqlQuery = QueryFactory.create(query);
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String bookName = solution.get("bookName").toString().substring(0, solution.get("bookName").toString().length() - 3);
                String bookLink = solution.get("s").toString();
                String authorName = solution.get("author").toString().substring(0, solution.get("author").toString().length() - 3);
                String authorLink = solution.get("authorLink").toString();
                if (!authorName.contains("(") ) {
                    if (!bookName.contains("(")) {
                        BookAuthor bookAuthor = new BookAuthor(bookName, bookLink, authorName, authorLink);
                        list.add(bookAuthor);
                    }
                }
            }
        }

        System.out.println(list);
        return list;



    }

    @Override
    public List<BookAuthor> getWithBookName(String userSearch) {  // prebaruvaj spored bookName
        List<BookAuthor> list = new ArrayList<>();
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        String query =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "PREFIX dbo: <http://dbpedia.org/ontology/> " +

                        "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +

                        "SELECT distinct ?s ?bookName ?authorLink ?author  WHERE { " +
                        "?s rdf:type dbo:Book; " +
                        "rdfs:label ?bookName; " +
                        "dbo:abstract ?bookAbstract; " +
                        "rdfs:comment ?comment; " +
                        "dbo:numberOfPages ?numberOfPages; " +

                        "dbo:author ?authorLink. " +
                        "?authorLink rdfs:label ?author; " +

                        "a dbo:Person; " +
                        "dbo:abstract ?Abstract; " +
                        "dbo:birthDate ?birthDate; " +
                        "dbo:deathDate ?deathDate; " +
                        "dbo:birthPlace ?placeOfBirthLink. " +
                        "?placeOfBirthLink rdfs:label ?PlaceOfBirth; " +

                        "geo:lat ?latitude; " +
                        "geo:long ?longitude. " +



                        "FILTER ( regex (str(?bookName), '" + userSearch + "', 'i') ). " +
                        "FILTER (lang(?author) = 'en') " +
                        "FILTER (lang(?bookName) = 'en') " +
                        "FILTER(lang(?Abstract) = 'en') " +
                        "FILTER(lang(?PlaceOfBirth) = 'en') " +
                        "FILTER(lang(?bookAbstract) = 'en') " +
                        "FILTER(lang(?comment) = 'en') " +
                        "} "+
                        "ORDER BY (?bookName)"+
                "LIMIT 51" ;


        Query sparqlQuery = QueryFactory.create(query);
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String bookName = solution.get("bookName").toString().substring(0,solution.get("bookName").toString().length()-3);
                String bookLink = solution.get("s").toString();
                String authorName = solution.get("author").toString().substring(0,solution.get("author").toString().length()-3);
                String authorLink = solution.get("authorLink").toString();

                if (!authorName.contains("(") ) {
                    if (!bookName.contains("(")) {
                        BookAuthor bookAuthor = new BookAuthor(bookName, bookLink, authorName, authorLink);
                        list.add(bookAuthor);
                    }
                }
            }
        }

        System.out.println(list);
        return list;
    }

    @Override
    public List<BookAuthor> getWithBookNameDesc(String userSearch) {
        List<BookAuthor> list = new ArrayList<>();
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
//        String query =
//                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
//                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
//                        "PREFIX ontology: <http://dbpedia.org/ontology/> " +
//                        "SELECT distinct ?s ?bookName ?authorLink ?author  WHERE { " +
//                        "?s rdf:type ontology:Book; " +
//                        "rdfs:label ?bookName; " +
//                        "ontology:author ?authorLink. " +
//                        "?authorLink rdfs:label ?author " +
//
//                        "FILTER ( regex (str(?bookName), '" + userSearch + "', 'i') ). " +
//                        "FILTER (lang(?author) = 'en') " +
//                        "FILTER (lang(?bookName) = 'en') " +
//                        "FILTER(lang(?Abstract) = 'en') " +
//                        "FILTER(lang(?PlaceOfBirth) = 'en') " +
//                        "FILTER(lang(?bookAbstract) = 'en') " +
//                        "FILTER(lang(?comment) = 'en') " +
//                        "} "+
//                        "ORDER BY DESC(?bookName)"+
//                        "LIMIT 43" ;


        String query =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "PREFIX dbo: <http://dbpedia.org/ontology/> " +

                        "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +

                        "SELECT distinct ?s ?bookName ?authorLink ?author  WHERE { " +
                        "?s rdf:type dbo:Book; " +
                        "rdfs:label ?bookName; " +
                        "dbo:abstract ?bookAbstract; " +
                        "rdfs:comment ?comment; " +
                        "dbo:numberOfPages ?numberOfPages; " +

                        "dbo:author ?authorLink. " +
                        "?authorLink rdfs:label ?author; " +

                        "a dbo:Person; " +
                        "dbo:abstract ?Abstract; " +
                        "dbo:birthDate ?birthDate; " +
                        "dbo:deathDate ?deathDate; " +
                        "dbo:birthPlace ?placeOfBirthLink. " +
                        "?placeOfBirthLink rdfs:label ?PlaceOfBirth; " +

                        "geo:lat ?latitude; " +
                        "geo:long ?longitude. " +

                        "FILTER ( regex (str(?bookName), '" + userSearch + "', 'i') ). " +
                        "FILTER (lang(?author) = 'en') " +
                        "FILTER (lang(?bookName) = 'en') " +
                        "FILTER(lang(?Abstract) = 'en') " +
                        "FILTER(lang(?PlaceOfBirth) = 'en') " +
                        "FILTER(lang(?bookAbstract) = 'en') " +
                        "FILTER(lang(?comment) = 'en') " +
                        "} "+
                        "ORDER BY DESC(?bookName)"+
                        "LIMIT 51" ;

        Query sparqlQuery = QueryFactory.create(query);
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String bookName = solution.get("bookName").toString().substring(0,solution.get("bookName").toString().length()-3);
                String bookLink = solution.get("s").toString();
                String authorName = solution.get("author").toString().substring(0,solution.get("author").toString().length()-3);
                String authorLink = solution.get("authorLink").toString();

                if (!authorName.contains("(") ) {
                    if (!bookName.contains("(")) {
                        BookAuthor bookAuthor = new BookAuthor(bookName, bookLink, authorName, authorLink);
                        list.add(bookAuthor);
                    }
                }
            }
        }

        System.out.println(list);
        return list;
    }

    @Override
    public List<BookAuthor> getWithAuthorName(String userSearch) {
        List<BookAuthor> list = new ArrayList<>();
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
//        String query =
//                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
//                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
//                        "PREFIX ontology: <http://dbpedia.org/ontology/> " +
//                        "SELECT distinct ?s ?bookName ?authorLink ?author  WHERE { " +
//                        "?s rdf:type ontology:Book; " +
//                        "rdfs:label ?bookName; " +
//                        "ontology:author ?authorLink. " +
//                        "?authorLink rdfs:label ?author " +
//
//                        "FILTER ( regex (str(?author), '" + userSearch + "', 'i') ). " +
//                        "FILTER (lang(?author) = 'en') " +
//                        "FILTER (lang(?bookName) = 'en') " +
//                        "FILTER(lang(?Abstract) = 'en') " +
//                        "FILTER(lang(?PlaceOfBirth) = 'en') " +
//                        "FILTER(lang(?bookAbstract) = 'en') " +
//                        "FILTER(lang(?comment) = 'en') " +
//                        "} "+
//                        //"ORDER BY ASC(?bookName)"+
//                        "ORDER BY ASC(?author)"+
//
//                        "LIMIT 43" ;


        String query =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "PREFIX dbo: <http://dbpedia.org/ontology/> " +

                        "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +

                        "SELECT distinct ?s ?bookName ?authorLink ?author  WHERE { " +
                        "?s rdf:type dbo:Book; " +
                        "rdfs:label ?bookName; " +
                        "dbo:abstract ?bookAbstract; " +
                        "rdfs:comment ?comment; " +
                        "dbo:numberOfPages ?numberOfPages; " +

                        "dbo:author ?authorLink. " +


                        "?authorLink rdfs:label ?author; " +

                        "a dbo:Person; " +
                        "dbo:abstract ?Abstract; " +
                        "dbo:birthDate ?birthDate; " +
                        "dbo:deathDate ?deathDate; " +
                        "dbo:birthPlace ?placeOfBirthLink. " +
                        "?placeOfBirthLink rdfs:label ?PlaceOfBirth; " +

                        "geo:lat ?latitude; " +
                        "geo:long ?longitude. " +

                        "FILTER ( regex (str(?author), '" + userSearch + "', 'i') ). " +
                        "FILTER (lang(?author) = 'en') " +
                        "FILTER (lang(?bookName) = 'en') " +
                        "FILTER(lang(?Abstract) = 'en') " +
                        "FILTER(lang(?PlaceOfBirth) = 'en') " +
                        "FILTER(lang(?bookAbstract) = 'en') " +
                        "FILTER(lang(?comment) = 'en') " +
                        "} "+
                        //"ORDER BY ASC(?bookName)"+
                        "ORDER BY ASC(?author)"+

                        "LIMIT 51" ;



        Query sparqlQuery = QueryFactory.create(query);
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String bookName = solution.get("bookName").toString().substring(0,solution.get("bookName").toString().length()-3);
                String bookLink = solution.get("s").toString();
                String authorName = solution.get("author").toString().substring(0,solution.get("author").toString().length()-3);
                String authorLink = solution.get("authorLink").toString();

                if (!authorName.contains("(") ) {
                    if (!bookName.contains("(")) {
                        BookAuthor bookAuthor = new BookAuthor(bookName, bookLink, authorName, authorLink);
                        list.add(bookAuthor);
                    }
                }
            }
        }

        System.out.println(list);
        return list;
    }

    @Override
    public List<BookAuthor> getWithAuthorNameDesc(String userSearch) {
        List<BookAuthor> list = new ArrayList<>();
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
//        String query =
//                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
//                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
//                        "PREFIX ontology: <http://dbpedia.org/ontology/> " +
//                        "SELECT distinct ?s ?bookName ?authorLink ?author  WHERE { " +
//                        "?s rdf:type ontology:Book; " +
//                        "rdfs:label ?bookName; " +
//                        "ontology:author ?authorLink. " +
//                        "?authorLink rdfs:label ?author " +
//
//                        "FILTER ( regex (str(?author), '" + userSearch + "', 'i') ). " +
//                        "FILTER (lang(?author) = 'en') " +
//                        "FILTER (lang(?bookName) = 'en') " +
//                        "FILTER(lang(?Abstract) = 'en') " +
//                        "FILTER(lang(?PlaceOfBirth) = 'en') " +
//                        "FILTER(lang(?bookAbstract) = 'en') " +
//                        "FILTER(lang(?comment) = 'en') " +
//                        "} "+
//                        //"ORDER BY DESC(?bookName)"+
//                        "ORDER BY DESC(?author)"+
//                        "LIMIT 43" ;


        String query =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "PREFIX dbo: <http://dbpedia.org/ontology/> " +

                        "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +

                        "SELECT distinct ?s ?bookName ?authorLink ?author  WHERE { " +
                        "?s rdf:type dbo:Book; " +
                        "rdfs:label ?bookName; " +
                        "dbo:abstract ?bookAbstract; " +
                        "rdfs:comment ?comment; " +
                        "dbo:numberOfPages ?numberOfPages; " +

                        "dbo:author ?authorLink. " +


                        "?authorLink rdfs:label ?author; " +

                        "a dbo:Person; " +
                        "dbo:abstract ?Abstract; " +
                        "dbo:birthDate ?birthDate; " +
                        "dbo:deathDate ?deathDate; " +
                        "dbo:birthPlace ?placeOfBirthLink. " +
                        "?placeOfBirthLink rdfs:label ?PlaceOfBirth; " +

                        "geo:lat ?latitude; " +
                        "geo:long ?longitude. " +

                        "FILTER ( regex (str(?author), '" + userSearch + "', 'i') ). " +
                        "FILTER (lang(?author) = 'en') " +
                        "FILTER (lang(?bookName) = 'en') " +
                        "FILTER(lang(?Abstract) = 'en') " +
                        "FILTER(lang(?PlaceOfBirth) = 'en') " +
                        "FILTER(lang(?bookAbstract) = 'en') " +
                        "FILTER(lang(?comment) = 'en') " +
                        "} "+
                        //"ORDER BY ASC(?bookName)"+
                        "ORDER BY DESC(?author)"+

                        "LIMIT 51" ;

        Query sparqlQuery = QueryFactory.create(query);
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String bookName = solution.get("bookName").toString().substring(0,solution.get("bookName").toString().length()-3);
                String bookLink = solution.get("s").toString();
                String authorName = solution.get("author").toString().substring(0,solution.get("author").toString().length()-3);
                String authorLink = solution.get("authorLink").toString();

                if (!authorName.contains("(") ) {
                    if (!bookName.contains("(")) {
                        BookAuthor bookAuthor = new BookAuthor(bookName, bookLink, authorName, authorLink);
                        list.add(bookAuthor);
                    }
                }
            }
        }

        System.out.println(list);
        return list;
    }
}
