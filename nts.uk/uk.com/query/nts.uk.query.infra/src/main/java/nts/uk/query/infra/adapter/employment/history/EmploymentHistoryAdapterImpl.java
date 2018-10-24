/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.adapter.employment.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.infra.entity.employment.history.BsymtEmploymentHist;
import nts.uk.query.model.employement.history.EmploymentHistoryAdapter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class EmploymentHistoryAdapterImpl.
 */
@Stateless
public class EmploymentHistoryAdapterImpl extends JpaRepository implements EmploymentHistoryAdapter {

	/** The Constant FIND_BY_EMPCDS_AND_PERIOD. */
	private static final String FIND_BY_EMPCDS_AND_PERIOD = "SELECT h FROM BsymtEmploymentHist h"
			+ " INNER JOIN BsymtEmploymentHistItem hi"
			+ " ON h.hisId = hi.hisId"
			+ " WHERE hi.empCode IN :empCds"
			+ " AND h.strDate <= :endDate"
			+ " AND :startDate <= h.endDate";

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.query.model.employement.history.EmploymentHistoryAdapter#
	 * findSIdsByEmpCdsAndPeriod(java.util.List,
	 * nts.uk.shr.com.time.calendar.period.DatePeriod)
	 */
	@Override
	public List<String> findSIdsByEmpCdsAndPeriod(List<String> empCds, DatePeriod period) {
		if (empCds.isEmpty())
			return Collections.emptyList();

		List<String> listEmployeeIds = new ArrayList<>();

		// Split query.
		CollectionUtil.split(empCds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			listEmployeeIds.addAll(this.queryProxy().query(FIND_BY_EMPCDS_AND_PERIOD, BsymtEmploymentHist.class)
					.setParameter("empCds", subList)
					.setParameter("startDate", period.start())
					.setParameter("endDate", period.end())
					.getList(e -> e.sid));
		});

		return listEmployeeIds;
	}

}
