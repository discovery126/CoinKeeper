package org.denis.coinkeeper.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "expenses")

//!!!!
//TODO:  timur: ExpensesEntity и ProfitEntity - по сути одно и тоже
// Я  бы сделал какой-то BalanceEntry c полем BalanceType (PROFIT и EXPENSE)
// Тогда количество дублированной логики сильно уменьшится

public class ExpensesEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expensesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column
    private String name;

    @Column
    private String category;

    @Column
    private Long price;

    @Column
    @Builder.Default
    private Instant AddedAt = Instant.now();
}
