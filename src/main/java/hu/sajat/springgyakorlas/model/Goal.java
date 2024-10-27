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
@Table(name = "goal")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "goal_name")
    private String goalName;

    @Column(name = "story")
    private String story;

    @Column(name = "target_amount")
    private Double targetAmount;

    @Column(name = "received_amount")
    private Double receivedAmount;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "exp_date")
    private LocalDateTime expDate;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "successful")
    private boolean successful;

    @Column(name = "goal_type")
    @Enumerated(EnumType.STRING)
    private GoalType goalType;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL)
    @Column(name = "incoming_transfers")
    private List<Transfer> incomingTransfers;
}
