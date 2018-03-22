/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employment;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSetRepository;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaEmpDeforLaborMonthActCalSetRepository extends JpaRepository
		implements EmpDeforLaborMonthActCalSetRepository {

	@Override
	public void add(EmpDeforLaborMonthActCalSet companyLaborDeforSetMonthly) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(EmpDeforLaborMonthActCalSet companyLaborDeforSetMonthly) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<EmpDeforLaborMonthActCalSet> find(String cid, String emplCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String cid, String empCode) {
		// TODO Auto-generated method stub

	}

}
