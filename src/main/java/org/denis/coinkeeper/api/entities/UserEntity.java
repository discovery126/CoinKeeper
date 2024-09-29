package org.denis.coinkeeper.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    @Builder.Default
    private Long account = 0L;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "currency_id")
    private CurrencyEntity currency;

    @Builder.Default
    @OneToMany
    //referencedColumnName - указываем название поля в java, а если есть @Column, то что внутри
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private List<ProfitEntity> profitList = new ArrayList<>();

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private List<ExpensesEntity> expensesList = new ArrayList<>();
}
