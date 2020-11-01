package pl.smola.scrapper;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import java.util.Optional;

@AutoValue
abstract class MirkoPost {
    abstract int plusCount();

    abstract String author();

    abstract String postContent();

    abstract ImmutableList<String> tags();

    abstract Optional<String> maybeImageUrl();

    static Builder builder() {
        return new AutoValue_MirkoPost.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        public abstract Builder plusCount(int plusCount);

        public abstract Builder author(String author);

        public abstract Builder postContent(String postContent);

        public abstract Builder tags(ImmutableList<String> tags);

        public abstract Builder maybeImageUrl(Optional<String> maybeImageUrl);

        public abstract MirkoPost build();
    }


}
