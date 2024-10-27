package hu.sajat.springgyakorlas.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transfer")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @Column(name = "amount")
    private Double amount;


    private Double amountCurrency;

    @Column(name = "approved")
    private boolean approved;


    @Column(name = "created_date", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdDate;

}
