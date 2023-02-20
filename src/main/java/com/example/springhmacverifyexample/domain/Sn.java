package com.example.springhmacverifyexample.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sn")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sn {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String sn;

    @Enumerated(EnumType.STRING)
    private SnStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_order_id")
    @ToString.Exclude
    @JsonIgnore
    private Order ordered;

    /* set max retry 3 */
    @JsonIgnore
    @Builder.Default
    private int retryCount = 1;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Sn sn = (Sn) o;
        return id != null && Objects.equals(id, sn.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Sn{" +
                "id='" + id + '\'' +
                ", sn='" + sn + '\'' +
                ", status=" + status +
                ", retryCount=" + retryCount +
                '}';
    }
}
