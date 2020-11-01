package pl.smola.scrapper;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Entity
final class MirkoScrapResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate scrapDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MirkoPost> posts;

    MirkoScrapResult() {
    }

    MirkoScrapResult(LocalDate scrapDate, List<MirkoPost> posts) {
        this.scrapDate = scrapDate;
        this.posts = posts;
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    LocalDate getScrapDate() {
        return scrapDate;
    }

    void setScrapDate(LocalDate scrapDate) {
        this.scrapDate = scrapDate;
    }

    List<MirkoPost> getPosts() {
        return posts;
    }

    void setPosts(List<MirkoPost> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MirkoScrapResult that = (MirkoScrapResult) o;
        return Objects.equals(scrapDate, that.scrapDate) &&
                Objects.equals(posts, that.posts);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
