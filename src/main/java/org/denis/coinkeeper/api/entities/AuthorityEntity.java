package org.denis.coinkeeper.api.entities;


import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authority")
public class AuthorityEntity {

    @Column(name = "authority_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorityId;

    @Column(name = "authority_name")
    private String authorityName;
}
