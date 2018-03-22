/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSetRepository;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaComRegulaMonthActCalSetRepository extends JpaRepository
		implements ComRegulaMonthActCalSetRepository {

	@Override
	public Optional<ComRegulaMonthActCalSet> getCompanyLaborRegSetMonthlyActualByCid(
			String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(ComRegulaMonthActCalSet companyLaborRegSetMonthlyActual) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ComRegulaMonthActCalSet companyLaborRegSetMonthlyActual) {
		// TODO Auto-generated method stub

	}

}
