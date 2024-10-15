package org.denis.coinkeeper.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currency")
public class CurrencyEntity {

    @Column(name = "currency_id") @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long currencyId;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "currency_description")
    private String currencyDescription;
}
