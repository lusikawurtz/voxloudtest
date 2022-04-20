package com.example.testewtje.model.entyties;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "tag")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @ManyToMany(cascade = CascadeType.ALL)
//    private List<ImageEntity> images;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TagEntity tagEntity = (TagEntity) o;
        return id != null && Objects.equals(id, tagEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
