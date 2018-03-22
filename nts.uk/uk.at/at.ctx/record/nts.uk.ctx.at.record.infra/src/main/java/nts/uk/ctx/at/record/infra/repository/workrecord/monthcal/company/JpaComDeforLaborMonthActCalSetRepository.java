/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSetRepository;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaComDeforLaborMonthActCalSetRepository extends JpaRepository
		implements ComDeforLaborMonthActCalSetRepository {

	@Override
	public Optional<ComDeforLaborMonthActCalSet> getCompanyLaborDeforSetMonthlyByCid(
			String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(ComDeforLaborMonthActCalSet companyLaborDeforSetMonthly) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ComDeforLaborMonthActCalSet companyLaborDeforSetMonthly) {
		// TODO Auto-generated method stub

	}

}
