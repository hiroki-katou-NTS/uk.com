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
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.company.KrcstComDeforMCalSet;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaComDeforLaborMonthActCalSetRepository extends JpaRepository
		implements ComDeforLaborMonthActCalSetRepository {

	private final String FIND_BY_COMPANY_ID = "SELECT a FROM KrcstDeforMCalSet a WHERE a.KrcstDeforMCalSet.companyId = :companyId";
	
	@Override
	public Optional<ComDeforLaborMonthActCalSet> find(String companyId) {
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

	@Override
	public void remove(String companyId) {
		// TODO Auto-generated method stub

	}

}
