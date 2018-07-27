/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.repository.employee.history;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.infra.entity.employee.history.BsymtAffCompanyHist;
import nts.uk.query.model.employee.history.EmployeeHistoryRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaEmployeeHistoryRepository.
 */
@Stateless
public class JpaEmployeeHistoryRepository extends JpaRepository implements EmployeeHistoryRepository {

	/** The Constant FIND_BY_ENTRY_DATE. */
	private static final String FIND_BY_ENTRY_DATE = "SELECT c FROM BsymtAffCompanyHist c"
			+ " WHERE c.startDate >= :startDate" 
			+ " AND c.startDate <= :endDate"
			+ " AND c.companyId = :comId";

	/** The Constant FIND_BY_RETIREMENT_DATE. */
	private static final String FIND_BY_RETIREMENT_DATE = "SELECT c FROM BsymtAffCompanyHist c"
			+ " WHERE c.endDate >= :startDate"
			+ " AND c.endDate <= :endDate"
			+ " AND c.companyId = :comId";

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.query.model.employee.history.EmployeeHistoryRepository#
	 * findEmployeeByEntryDate(nts.uk.shr.com.time.calendar.period.DatePeriod)
	 */
	@Override
	public List<String> findEmployeeByEntryDate(String comId, DatePeriod period) {
		return this.queryProxy().query(FIND_BY_ENTRY_DATE, BsymtAffCompanyHist.class)
				.setParameter("startDate", period.start())
				.setParameter("endDate", period.end())
				.setParameter("comId", comId)
				.getList().stream()
				.map(e -> e.bsymtAffCompanyHistPk.sId).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.query.model.employee.history.EmployeeHistoryRepository#
	 * findEmployeeByRetirementDate(nts.uk.shr.com.time.calendar.period.
	 * DatePeriod)
	 */
	@Override
	public List<String> findEmployeeByRetirementDate(String comId, DatePeriod period) {
		return this.queryProxy().query(FIND_BY_RETIREMENT_DATE, BsymtAffCompanyHist.class)
				.setParameter("startDate", period.start())
				.setParameter("endDate", period.end())
				.setParameter("comId", comId)
				.getList().stream()
				.map(e -> e.bsymtAffCompanyHistPk.sId).collect(Collectors.toList());
	}

}
