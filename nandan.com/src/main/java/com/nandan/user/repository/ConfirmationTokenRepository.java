package com.nandan.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nandan.user.ConfirmationToken;

@Transactional(readOnly = true)
@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

	@Query("select c from ConfirmationToken c where c.token=:token")
	ConfirmationToken findByToken(@Param("token") String token);

}
