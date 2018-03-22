/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSetRepository;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaWkpFlexMonthActCalSetRepository extends JpaRepository
		implements WkpFlexMonthActCalSetRepository {

	@Override
	public void add(WkpFlexMonthActCalSet companyCalMonthlyFlex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(WkpFlexMonthActCalSet companyCalMonthlyFlex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<WkpFlexMonthActCalSet> findByCidAndWkpId(String cid, String wkpId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(WkpFlexMonthActCalSet wkpCalSetMonthlyActualFlex) {
		// TODO Auto-generated method stub
		
	}

}
