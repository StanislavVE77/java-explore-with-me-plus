package ru.yandex.practicum.ewm.model;

import jakarta.persistence.*;
import lombok.*;
//import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
//@DynamicUpdate
@Table(name = "stat", schema = "public")
@Getter
@Setter
@ToString
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app")
    private String app;

    @Column(name = "uri")
    private String uri;

    @Column(name = "ip")
    private String ip;

    @Column(name = "created")
    private LocalDateTime created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stat)) return false;
        return id != null && id.equals(((Stat) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
