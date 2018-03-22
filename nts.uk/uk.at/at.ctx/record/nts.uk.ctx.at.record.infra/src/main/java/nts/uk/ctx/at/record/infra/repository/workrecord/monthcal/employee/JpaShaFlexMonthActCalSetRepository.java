/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaFlexMonthActCalSetRepository;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaShaFlexMonthActCalSetRepository extends JpaRepository
		implements ShaFlexMonthActCalSetRepository {

	@Override
	public void add(ShaFlexMonthActCalSet companyCalMonthlyFlex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ShaFlexMonthActCalSet companyCalMonthlyFlex) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ShaFlexMonthActCalSet> find(String cid, String empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String cid, String sId) {
		// TODO Auto-generated method stub

	}

}
