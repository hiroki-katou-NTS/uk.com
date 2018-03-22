/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employment;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSetRepository;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaEmpFlexMonthActCalSetRepository extends JpaRepository
		implements EmpFlexMonthActCalSetRepository {

	@Override
	public void add(EmpFlexMonthActCalSet companyCalMonthlyFlex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(EmpFlexMonthActCalSet companyCalMonthlyFlex) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<EmpFlexMonthActCalSet> find(String cid, String emplCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String cid, String empCode) {
		// TODO Auto-generated method stub

	}

}
