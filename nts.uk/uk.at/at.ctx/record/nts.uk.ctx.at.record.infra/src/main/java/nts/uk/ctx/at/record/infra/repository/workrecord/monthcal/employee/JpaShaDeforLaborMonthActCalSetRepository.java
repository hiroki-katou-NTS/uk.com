/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSetRepository;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaShaDeforLaborMonthActCalSetRepository extends JpaRepository
		implements ShaDeforLaborMonthActCalSetRepository {

	@Override
	public void add(ShaDeforLaborMonthActCalSet companyLaborDeforSetMonthly) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ShaDeforLaborMonthActCalSet companyLaborDeforSetMonthly) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ShaDeforLaborMonthActCalSet> findEmpLaborDeforSetTempByCidAndEmpId(String cid,
			String empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(ShaDeforLaborMonthActCalSet EmpLaborDeforSetTemp) {
		// TODO Auto-generated method stub

	}

}
