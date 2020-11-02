package pl.smola.scrapper;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
final class MirkoScrapResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime scrapDateTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MirkoPost> posts;

    MirkoScrapResult() {
    }

    MirkoScrapResult(LocalDateTime scrapDateTime, List<MirkoPost> posts) {
        this.scrapDateTime = scrapDateTime;
        this.posts = posts;
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    LocalDateTime getScrapDateTime() {
        return scrapDateTime;
    }

    void setScrapDateTime(LocalDateTime scrapDateTime) {
        this.scrapDateTime = scrapDateTime;
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
        return Objects.equals(scrapDateTime, that.scrapDateTime) &&
                Objects.equals(posts, that.posts);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
