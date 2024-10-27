package hu.sajat.springgyakorlas.repository;

import hu.sajat.springgyakorlas.model.Transfer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends CrudRepository<Transfer, Long> {
    @Query("SELECT t FROM Transfer t WHERE t.approved = false AND t.account.id = ?1")
    List<Transfer> findTransfersByApprovedIsFalseWhereAccountId(Long id);
}
