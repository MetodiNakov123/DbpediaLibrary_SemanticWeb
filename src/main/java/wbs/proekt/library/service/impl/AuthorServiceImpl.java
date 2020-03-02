package wbs.proekt.library.service.impl;

import org.apache.jena.query.*;
import org.springframework.stereotype.Service;
import wbs.proekt.library.models.Author;
import wbs.proekt.library.models.Book;
import wbs.proekt.library.models.BookAuthor;
import wbs.proekt.library.repository.AuthorRepository;
import wbs.proekt.library.service.AuthorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Optional<Author> findByAuthorLink(String link) {
        return authorRepository.findByAuthorLink(link);
    }

    @Override
    public Author getAuthorDetailsSPARQL(String link) {

        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        String query =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                        "PREFIX dbo: <http://dbpedia.org/ontology/> " +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +

                        "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +

                        "SELECT distinct ?authorLink ?Abstract ?birthDate ?deathDate ?authorName ?placeOfBirthLink ?PlaceOfBirth ?latitude ?longitude WHERE { " +
                        "?authorLink a dbo:Person; " +
                        "dbo:abstract ?Abstract; " +
                        "dbo:birthDate ?birthDate; " +
                        "dbo:deathDate ?deathDate; " +
                        "rdfs:label ?authorName; " +
                        "dbo:birthPlace ?placeOfBirthLink. " +
                        "?placeOfBirthLink rdfs:label ?PlaceOfBirth; " +

                        "geo:lat ?latitude; " +
                        "geo:long ?longitude. " +


                        "FILTER(regex (str(?authorLink), '" + link + "', 'i') ). " +  //         i -> insensitive
                        "FILTER(lang(?authorName) = 'en') " +
                        "FILTER(lang(?Abstract) = 'en') " +
                        "FILTER(lang(?PlaceOfBirth) = 'en') " +
                        "}";



        // SELECT distinct  ?Abstract ?birthDate ?deathDate  ?placeOfBirthLink ?PlaceOfBirth

        Author author = new Author();
        Query sparqlQuery = QueryFactory.create(query);
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String authorName = solution.get("authorName").toString().substring(0,solution.get("authorName").toString().length()-3);
                String authorLink = solution.get("authorLink").toString();
                String Abstract = solution.get("Abstract").toString().substring(0,solution.get("Abstract").toString().length()-3);
                String birthDate = solution.get("birthDate").toString().substring(0,solution.get("birthDate").toString().length()-39); // brisenje na tipot
                String deathDate = solution.get("deathDate").toString().substring(0,solution.get("deathDate").toString().length()-39);
                String placeOfBirthLink = solution.get("placeOfBirthLink").toString();

                String latitude = solution.get("latitude").toString().substring(0,solution.get("latitude").toString().length()-40); // ?latitude ?longitude
                String longitude = solution.get("longitude").toString().substring(0,solution.get("longitude").toString().length()-40);
                String PlaceOfBirth = solution.get("PlaceOfBirth").toString().substring(0,solution.get("PlaceOfBirth").toString().length()-3);

                //Author author = new Author(authorName,authorLink,placeOfBirthLink,PlaceOfBirth,Abstract,birthDate,deathDate);
                author.setAuthorName(authorName);
                author.setAuthorLink(authorLink);
                author.setPlaceOfBirthLink(placeOfBirthLink);
                author.setPlaceOfBirth(PlaceOfBirth);
                author.setAbstract(Abstract);
                author.setBirthDate(birthDate);
                author.setDeathDate(deathDate);

                author.setLongitude(longitude);
                author.setLatitude(latitude);
            }
        }

        return author;

    }

    @Override
    public Author getAuthorDetailsNAME(String name) {

            String SPARQLEndpoint = "https://dbpedia.org/sparql";
            String query =
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                            "PREFIX dbo: <http://dbpedia.org/ontology/> " +
                            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +

                            "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +

                            "SELECT distinct ?authorLink ?Abstract ?birthDate ?deathDate ?authorName ?placeOfBirthLink ?PlaceOfBirth ?latitude ?longitude WHERE { " +
                            "?authorLink a dbo:Person; " +
                            "dbo:abstract ?Abstract; " +
                            "dbo:birthDate ?birthDate; " +
                            "dbo:deathDate ?deathDate; " +
                            //"rdfs:label '"+name+"';" +
                            "rdfs:label ?authorName; " +

                            "dbo:birthPlace ?placeOfBirthLink. " +
                            "?placeOfBirthLink rdfs:label ?PlaceOfBirth; " +

                            "geo:lat ?latitude; " +
                            "geo:long ?longitude. " +

                            "FILTER(lang(?authorName) = 'en') " +
                            "FILTER(regex ('" + name + "', str(?authorName),  'i') ). " + // poradi sho se dodava na krajot @en u dbpedia

                            "FILTER(lang(?Abstract) = 'en') " +
                            "FILTER(lang(?PlaceOfBirth) = 'en') " +
                            "}" +
                            "LIMIT 1"
                    ;



            // SELECT distinct  ?Abstract ?birthDate ?deathDate  ?placeOfBirthLink ?PlaceOfBirth

            Author author = new Author();
            Query sparqlQuery = QueryFactory.create(query);
            try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
                ResultSet resultSet = queryExecution.execSelect();
                while (resultSet.hasNext()) {
                    QuerySolution solution = resultSet.nextSolution();
                    String authorName = solution.get("authorName").toString().substring(0,solution.get("authorName").toString().length()-3);
                    String authorLink = solution.get("authorLink").toString();
                    String Abstract = solution.get("Abstract").toString().substring(0,solution.get("Abstract").toString().length()-3);
                    String birthDate = solution.get("birthDate").toString().substring(0,solution.get("birthDate").toString().length()-39); // brisenje na tipot
                    String deathDate = solution.get("deathDate").toString().substring(0,solution.get("deathDate").toString().length()-39);
                    String placeOfBirthLink = solution.get("placeOfBirthLink").toString();

                    String latitude = solution.get("latitude").toString().substring(0,solution.get("latitude").toString().length()-40); // ?latitude ?longitude
                    String longitude = solution.get("longitude").toString().substring(0,solution.get("longitude").toString().length()-40);
                    String PlaceOfBirth = solution.get("PlaceOfBirth").toString().substring(0,solution.get("PlaceOfBirth").toString().length()-3);

                    //Author author = new Author(authorName,authorLink,placeOfBirthLink,PlaceOfBirth,Abstract,birthDate,deathDate);
                    author.setAuthorName(authorName);
                    author.setAuthorLink(authorLink);
                    author.setPlaceOfBirthLink(placeOfBirthLink);
                    author.setPlaceOfBirth(PlaceOfBirth);
                    author.setAbstract(Abstract);
                    author.setBirthDate(birthDate);
                    author.setDeathDate(deathDate);

                    author.setLongitude(longitude);
                    author.setLatitude(latitude);
                }
            }

            return author;

        }

    @Override
    public List<BookAuthor> getBooksByAuthor(String author) {
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

                        "FILTER ( regex (str(?author), '" + author + "', 'i') ). " +
                        "FILTER (lang(?author) = 'en') " +
                        "FILTER (lang(?bookName) = 'en') " +
                        "FILTER(lang(?Abstract) = 'en') " +
                        "FILTER(lang(?PlaceOfBirth) = 'en') " +
                        "FILTER(lang(?bookAbstract) = 'en') " +
                        "FILTER(lang(?comment) = 'en') " +
                        "} "+
                        //"ORDER BY ASC(?bookName)"+
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
    public List<BookAuthor> getBooksDescByAuthor(String author) {
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

                        "FILTER ( regex (str(?author), '" + author + "', 'i') ). " +
                        "FILTER (lang(?author) = 'en') " +
                        "FILTER (lang(?bookName) = 'en') " +
                        "FILTER(lang(?Abstract) = 'en') " +
                        "FILTER(lang(?PlaceOfBirth) = 'en') " +
                        "FILTER(lang(?bookAbstract) = 'en') " +
                        "FILTER(lang(?comment) = 'en') " +
                        "} "+
                        //"ORDER BY ASC(?bookName)"+
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
}
