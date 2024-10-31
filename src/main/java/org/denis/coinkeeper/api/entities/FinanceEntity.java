package org.denis.coinkeeper.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "finance")
public class FinanceEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long financeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column
    private String name;

    @Column
    private String category;

    @Column
    private Double price;

    @Column
    private String financeType;

    @Column
    @Builder.Default
    private LocalDateTime addedAt = LocalDateTime.now();
}
