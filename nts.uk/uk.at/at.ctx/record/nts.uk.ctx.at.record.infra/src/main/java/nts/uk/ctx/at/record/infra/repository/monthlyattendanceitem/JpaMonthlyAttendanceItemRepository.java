/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.monthlyattendanceitem;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyattendanceitem.MonthlyAttendanceItem;
import nts.uk.ctx.at.record.dom.monthlyattendanceitem.MonthlyAttendanceItemRepository;

/**
 * The Class JpaMonthlyAttendanceItemRepository.
 */
@Stateless
public class JpaMonthlyAttendanceItemRepository extends JpaRepository implements MonthlyAttendanceItemRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.monthlyattendanceitem.
	 * MonthlyAttendanceItemRepository#findByAtr(java.lang.String, int)
	 */
	@Override
	public List<MonthlyAttendanceItem> findByAtr(String companyId, int itemAtr) {
		return Collections.emptyList();// TODO wait for entity
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.monthlyattendanceitem.
	 * MonthlyAttendanceItemRepository#findAll(java.lang.String)
	 */
	@Override
	public List<MonthlyAttendanceItem> findAll(String companyId) {
		return Collections.emptyList();// TODO wait for entity
	}
}
