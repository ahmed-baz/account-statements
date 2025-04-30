package com.demo.tree.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "statements")
public class StatementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "statements_seq")
    @SequenceGenerator(name = "statements_seq", sequenceName = "statements_seq", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;
    @Column(name = "statement_date")
    private Date date;
    private Double amount;
}
