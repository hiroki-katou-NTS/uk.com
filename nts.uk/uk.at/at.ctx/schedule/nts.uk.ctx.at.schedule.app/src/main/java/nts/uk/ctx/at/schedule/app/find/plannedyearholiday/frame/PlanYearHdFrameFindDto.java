/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.plannedyearholiday.frame;

import lombok.Data;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameName;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameNo;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * Instantiates a new plan year hd frame find dto.
 */
@Data
public class PlanYearHdFrameFindDto implements PlanYearHolidayFrameSetMemento {
	
	/** The plan year hd frame no. */
	private int planYearHdFrameNo;
	
	/** The plan year hd frame name. */
	private String planYearHdFrameName;
	
	/** The use atr. */
	private int useAtr;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		//no coding
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameSetMemento#setPlanYearHolidayFrameNo(nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameNo)
	 */
	@Override
	public void setPlanYearHolidayFrameNo(PlanYearHolidayFrameNo planYearHolidayFrNo) {
		this.planYearHdFrameNo = planYearHolidayFrNo.v().intValue();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameSetMemento#setUseClassification(nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr)
	 */
	@Override
	public void setUseClassification(NotUseAtr useAtr) {
		this.useAtr = useAtr.value;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameSetMemento#setPlanYearHolidayFrameName(nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameName)
	 */
	@Override
	public void setPlanYearHolidayFrameName(PlanYearHolidayFrameName planYearHolidayFrName) {
		this.planYearHdFrameName = planYearHolidayFrName.v();
	}

}
