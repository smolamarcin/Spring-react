package pl.smola.scrapper;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
final class MirkoPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int plusCount;

    private String author;

    @Column(columnDefinition = "LONGTEXT")
    private String postContent;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> tags;

    private String imageUrl;


    MirkoPost() {
    }

    public static final class MirkoPostBuilder {
        private int plusCount;
        private String author;
        private String postContent;
        private Set<String> tags;
        private String imageUrl;

        private MirkoPostBuilder() {
        }

        public static MirkoPostBuilder aMirkoPost() {
            return new MirkoPostBuilder();
        }

        public MirkoPostBuilder setPlusCount(int plusCount) {
            this.plusCount = plusCount;
            return this;
        }

        public MirkoPostBuilder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public MirkoPostBuilder setPostContent(String postContent) {
            this.postContent = postContent;
            return this;
        }

        public MirkoPostBuilder setTags(Set<String> tags) {
            this.tags = tags;
            return this;
        }

        public MirkoPostBuilder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public MirkoPost build() {
            MirkoPost mirkoPost = new MirkoPost();
            mirkoPost.setPlusCount(plusCount);
            mirkoPost.setAuthor(author);
            mirkoPost.setPostContent(postContent);
            mirkoPost.setTags(tags);
            mirkoPost.imageUrl = this.imageUrl;
            return mirkoPost;
        }
    }




    int getPlusCount() {
        return plusCount;
    }

    void setPlusCount(int plusCount) {
        this.plusCount = plusCount;
    }

    String getAuthor() {
        return author;
    }

    void setAuthor(String author) {
        this.author = author;
    }

    String getPostContent() {
        return postContent;
    }

    void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    Set<String> getTags() {
        return tags;
    }

    void setTags(Set<String> tags) {
        this.tags = tags;
    }

    String getMaybeImageUrl() {
        return imageUrl;
    }

    void setMaybeImageUrl(String maybeImageUrl) {
        this.imageUrl = maybeImageUrl;
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MirkoPost mirkoPost = (MirkoPost) o;
        return plusCount == mirkoPost.plusCount &&
                Objects.equals(author, mirkoPost.author) &&
                Objects.equals(postContent, mirkoPost.postContent) &&
                Objects.equals(tags, mirkoPost.tags) &&
                Objects.equals(imageUrl, mirkoPost.imageUrl);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
