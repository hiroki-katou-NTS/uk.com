/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSetRepository;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaWkpDeforLaborMonthActCalSetRepository extends JpaRepository
		implements WkpDeforLaborMonthActCalSetRepository {

	@Override
	public void add(WkpDeforLaborMonthActCalSet companyLaborDeforSetMonthly) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(WkpDeforLaborMonthActCalSet companyLaborDeforSetMonthly) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<WkpDeforLaborMonthActCalSet> findByCidAndWkpid(String cid, String wkpId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(WkpDeforLaborMonthActCalSet wkpTransLaborSetMonthly) {
		// TODO Auto-generated method stub

	}

}
