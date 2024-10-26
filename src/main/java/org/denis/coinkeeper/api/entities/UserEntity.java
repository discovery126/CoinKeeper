package org.denis.coinkeeper.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<FinanceEntity> financeList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_authority",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<AuthorityEntity> authorities;

    public void addFinance(FinanceEntity financeEntity) {
        financeList.add(financeEntity);
        financeEntity.setUser(this);
    }

    public void removeFinance(FinanceEntity financeEntity) {
        financeList.remove(financeEntity);
        financeEntity.setUser(null);
    }

}
//referencedColumnName - указываем название поля в java, а если есть @Column, то что внутри