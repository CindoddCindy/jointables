package tessjoinss.tesjointables.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comments")
public class Comment extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    //@Lob
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Post post;

    @OneToMany(targetEntity=CommentChild.class, mappedBy="comments")
    @OrderBy("name ASC")
    private Set<CommentChild> commentChildSet = new HashSet<CommentChild>();

    public Set<CommentChild> getCommentChildSet() {
        return commentChildSet;
    }

    public void setCommentChildSet(Set<CommentChild> commentChildSet) {
        this.commentChildSet = commentChildSet;
    }

    public Comment() {
    }

    public Comment(@NotNull String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment [id=" + id + ", text=" + text + ", post=" + post
                + "]";
    }
}
