package com.demo.tree.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_seq")
    @SequenceGenerator(name = "accounts_seq", sequenceName = "accounts_seq", allocationSize = 1)
    private Long id;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "account_number")
    private String accountNumber;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<StatementEntity> statements;
}
