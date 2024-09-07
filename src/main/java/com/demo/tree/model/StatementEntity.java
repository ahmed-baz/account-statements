package com.demo.tree.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "statement")
public class StatementEntity {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;
    @Column(name = "datefield")
    private String date;
    private Double amount;
}
