/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSetRepository;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaShaRegulaMonthActCalSetRepository extends JpaRepository
		implements ShaRegulaMonthActCalSetRepository {

	@Override
	public void add(ShaRegulaMonthActCalSet companyLaborRegSetMonthlyActual) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ShaRegulaMonthActCalSet companyLaborRegSetMonthlyActual) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ShaRegulaMonthActCalSet> find(String cid,
			String empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(ShaRegulaMonthActCalSet empRegSetMonthlyActual) {
		// TODO Auto-generated method stub

	}

}
