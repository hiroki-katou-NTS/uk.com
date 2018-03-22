/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSetRepository;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaWkpRegulaMonthActCalSetRepository extends JpaRepository
		implements WkpRegulaMonthActCalSetRepository {

	@Override
	public void add(WkpRegulaMonthActCalSet companyLaborRegSetMonthlyActual) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(WkpRegulaMonthActCalSet companyLaborRegSetMonthlyActual) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<WkpRegulaMonthActCalSet> findByCidAndWkpId(String cid, String wkpId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(WkpRegulaMonthActCalSet wkpRegularSetMonthlyActualWork) {
		// TODO Auto-generated method stub

	}

}
