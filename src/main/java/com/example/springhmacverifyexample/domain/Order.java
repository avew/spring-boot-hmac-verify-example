package com.example.springhmacverifyexample.domain;

import com.example.springhmacverifyexample.config.InstantUtcDeserializer;
import com.example.springhmacverifyexample.config.InstantUtcSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//if status is ORDER SN already get, SN status UNUSED
//if status is ALREADY_STAMP, SN already to stamp and if success status is COMPLETED and sn set to USED
//if status is RETRY, stamping process need retry, if failed retry SN status is FAILED (need refund) and Transaction status set to FAILED
//if order status is expired date, set status to EXPIRE and SN set to FAILED

@Entity
@Table(name = "t_order", uniqueConstraints = {@UniqueConstraint(columnNames = "ref_id")})
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "status", "ref_id", "account", "expired_date"})
@Slf4j
public class Order implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @JsonProperty(value = "ref_id")
    @Column(name = "ref_id")
    private String refId;

    private String reference;


    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TransactionStatus status = TransactionStatus.ORDER;

    /* default duration is day and duration time is 2 */
    @Builder.Default
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Duration duration = Duration.DAY;

    @Builder.Default
    @JsonIgnore
    private int durationTime = 2;


    @JsonSerialize(using = InstantUtcSerializer.class)
    @JsonDeserialize(using = InstantUtcDeserializer.class)
    @JsonProperty(value = "expired_date")
    private Instant expiredDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ordered")
    @Builder.Default
    private Set<Sn> sn = new HashSet<>();

    public Instant calculateExpire() {
        log.info("SET EXPIRE DURATION TIME={}, WITH DURATION={}", this.durationTime, this.duration);
        switch (this.duration) {
            case MINUTE:
                return Instant.now().plus(this.durationTime, ChronoUnit.MINUTES);
            case HOUR:
                return Instant.now().plus(this.durationTime, ChronoUnit.HOURS);
            case DAY:
                return Instant.now().plus(this.durationTime, ChronoUnit.DAYS);
            default:
                return Instant.now().plus(2, ChronoUnit.DAYS);
        }
    }

    public void add(Sn item) {
        if (item != null) {
            if (sn == null) {
                sn = new HashSet<>();
            }
            //item.setOrdered(this);
            sn.add(item);
        }
    }

    public void remove(Sn item) {
        if (item != null) {
            if (sn == null) {
                sn = new HashSet<>();
            }
            //item.setOrdered(this);
            sn.remove(item);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
