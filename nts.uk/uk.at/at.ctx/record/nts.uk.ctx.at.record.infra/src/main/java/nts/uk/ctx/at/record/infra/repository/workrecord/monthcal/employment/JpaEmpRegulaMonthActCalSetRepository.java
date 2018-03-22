/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employment;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSetRepository;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaEmpRegulaMonthActCalSetRepository extends JpaRepository
		implements EmpRegulaMonthActCalSetRepository {

	@Override
	public void add(EmpRegulaMonthActCalSet companyLaborRegSetMonthlyActual) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(EmpRegulaMonthActCalSet companyLaborRegSetMonthlyActual) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<EmpRegulaMonthActCalSet> find(String cid, String emplCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String cid, String empCode) {
		// TODO Auto-generated method stub

	}

}
