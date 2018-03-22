/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSetRepository;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaComFlexMonthActCalSetRepository extends JpaRepository
		implements ComFlexMonthActCalSetRepository {

	@Override
	public Optional<ComFlexMonthActCalSet> find(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(ComFlexMonthActCalSet companyCalMonthlyFlex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ComFlexMonthActCalSet companyCalMonthlyFlex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String companyId) {
		// TODO Auto-generated method stub
		
	}

}
