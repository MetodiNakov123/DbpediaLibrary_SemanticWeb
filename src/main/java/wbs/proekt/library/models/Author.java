package wbs.proekt.library.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorName;

    private String authorLink;

    private String placeOfBirthLink;

    private String placeOfBirth;


    private String Abstract;
    private String birthDate;
    private String deathDate;

    private String latitude;
    private String longitude;


    public Author() {

    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorLink() {
        return authorLink;
    }

    public void setAuthorLink(String authorLink) {
        this.authorLink = authorLink;
    }

    public String getPlaceOfBirthLink() {
        return placeOfBirthLink;
    }

    public void setPlaceOfBirthLink(String placeOfBirthLink) {
        this.placeOfBirthLink = placeOfBirthLink;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public Author(String authorName, String authorLink, String placeOfBirthLink, String placeOfBirth, String anAbstract, String birthDate, String deathDate, String latitude, String longitude) {
        this.authorName = authorName;
        this.authorLink = authorLink;
        this.placeOfBirthLink = placeOfBirthLink;
        this.placeOfBirth = placeOfBirth;
        this.Abstract = anAbstract;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
