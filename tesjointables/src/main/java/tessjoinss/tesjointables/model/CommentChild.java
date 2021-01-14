package tessjoinss.tesjointables.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "commentschild")
public class CommentChild extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    //@Lob
    private String text;
/*
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id", nullable = false)
    @JoinColumn(name = "id_post", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Comment comment;

 */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name="id", referencedColumnName="id"),
            @JoinColumn(name="post_id", referencedColumnName="post_id")
    })
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Comment comment;


    public CommentChild() {
    }

    public CommentChild(@NotNull String text, Comment comment) {
        this.text = text;
        this.comment = comment;
    }

    public CommentChild(@NotNull String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentChild [id=" + id + ", text=" + text + "]";
    }
}
