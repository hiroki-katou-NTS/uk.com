/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.plannedyearholiday.frame;

import java.math.BigDecimal;

import lombok.Data;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameGetMemento;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameName;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameNo;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * Instantiates a new plan year hd frame command dto.
 */
@Data

public class PlanYearHdFrameCommandDto implements PlanYearHolidayFrameGetMemento {
	
	/** The plan year hd frame no. */
	private int planYearHdFrameNo;
	
	/** The plan year hd frame name. */
	private String planYearHdFrameName;
	
	/** The use atr. */
	private int useAtr;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameGetMemento#getPlanYearHolidayFrameNo()
	 */
	@Override
	public PlanYearHolidayFrameNo getPlanYearHolidayFrameNo() {
		return new PlanYearHolidayFrameNo(BigDecimal.valueOf(this.planYearHdFrameNo));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameGetMemento#getUseClassification()
	 */
	@Override
	public NotUseAtr getUseClassification() {
		// TODO Auto-generated method stub
		return NotUseAtr.valueOf(this.useAtr);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameGetMemento#getPlanYearHolidayFrameName()
	 */
	@Override
	public PlanYearHolidayFrameName getPlanYearHolidayFrameName() {
		return new PlanYearHolidayFrameName(this.planYearHdFrameName);
	}
}
