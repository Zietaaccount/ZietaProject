package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ExpenseTypeMaster;

@Repository
public interface ExpenseTypeMasterRepository extends JpaRepository<ExpenseTypeMaster, Long> {

	List<ExpenseTypeMaster> findByIsDelete(short notDeleted);


	List<ExpenseTypeMaster> findByClientId(long clientId);
	
	List<ExpenseTypeMaster> findByClientIdAndIsDelete(Long clientId, short notDeleted);

	long deleteByExpenseType(String expenseType);


}
