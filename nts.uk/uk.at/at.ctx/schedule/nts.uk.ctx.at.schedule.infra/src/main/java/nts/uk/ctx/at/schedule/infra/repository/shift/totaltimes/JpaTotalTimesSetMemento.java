/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.totaltimes;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.totaltimes.CountAtr;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.SummaryAtr;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalCondition;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjects;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesABName;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesName;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.UseAtr;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalTimes;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaTotalTimesSetMemento.
 */
public class JpaTotalTimesSetMemento implements TotalTimesSetMemento {

	/** The entity. */
	private KshstTotalTimes entity;

	/**
	 * Instantiates a new jpa total times set memento.
	 *	
	 * @param totalTimes
	 *            the total times
	 */
	public JpaTotalTimesSetMemento(KshstTotalTimes totalTimes) {
		this.entity = totalTimes;
	}

	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTotalCountNo(Integer totalCountNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCountAtr(CountAtr countAtr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUseAtr(UseAtr useAtr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTotalTimesName(TotalTimesName totalTimesName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTotalTimesABName(TotalTimesABName totalTimesABName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSummaryAtr(SummaryAtr summaryAtr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTotalCondition(TotalCondition totalCondition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTotalSubjects(List<TotalSubjects> summaryList) {
		// TODO Auto-generated method stub

	}

}
