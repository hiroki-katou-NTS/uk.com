/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.workdayoff.frame;

import java.math.BigDecimal;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameName;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameNo;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkdayoffFrameCommandDto.
 */
@Data

public class WorkdayoffFrameCommandDto implements WorkdayoffFrameGetMemento {
	
	/** The workdayoff fr no. */
	private int workdayoffFrNo;
	
	/** The workdayoff fr name. */
	private String workdayoffFrName;
	
	/** The transfer fr name. */
	private String transferFrName;
	
	/** The use atr. */
	private int useAtr;
	
	/** The role. */
	private int role;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameGetMemento#getUseClassification()
	 */
	@Override
	public NotUseAtr getUseClassification() {
		return NotUseAtr.valueOf(this.useAtr);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getWorkdayoffFrameNo()
	 */
	@Override
	public WorkdayoffFrameNo getWorkdayoffFrameNo() {
		return new WorkdayoffFrameNo(BigDecimal.valueOf(this.workdayoffFrNo));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getTransferFrameName()
	 */
	@Override
	public WorkdayoffFrameName getTransferFrameName() {
		return new WorkdayoffFrameName(this.transferFrName);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getWorkdayoffFrameName()
	 */
	@Override
	public WorkdayoffFrameName getWorkdayoffFrameName() {
		return new WorkdayoffFrameName(this.workdayoffFrName);
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento#getRole()
	 */
	@Override
	public WorkdayoffFrameRole getRole() {
		return WorkdayoffFrameRole.valueOf(this.role);
	}
}
