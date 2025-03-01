package hu.sajat.springgyakorlas.repository;

import hu.sajat.springgyakorlas.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findAccountByEmail(String email);

    boolean existsByEmail(String email);
}
