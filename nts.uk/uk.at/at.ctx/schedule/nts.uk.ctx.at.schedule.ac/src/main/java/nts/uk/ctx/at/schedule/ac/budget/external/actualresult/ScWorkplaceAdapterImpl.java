/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ac.budget.external.actualresult;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.ScWorkplaceAdapter;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;

/**
 * The Class ScWorkplaceAdapterImpl.
 */
@Stateless
public class ScWorkplaceAdapterImpl implements ScWorkplaceAdapter {

	/** The workplace pub. */
	@Inject
	private SyWorkplacePub workplacePub;

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.budget.external.actualresult.WorkplaceAdapter#
	 * findWpkIdList(java.lang.String, java.util.Date)
	 */
	@Override
	public List<String> findWpkIdList(String companyId, String wpkCode, Date baseDate) {
		GeneralDate generalDate = GeneralDate.legacyDate(baseDate);
		return this.workplacePub.findWpkIdsByWkpCode(companyId, wpkCode, generalDate);
	}

}
