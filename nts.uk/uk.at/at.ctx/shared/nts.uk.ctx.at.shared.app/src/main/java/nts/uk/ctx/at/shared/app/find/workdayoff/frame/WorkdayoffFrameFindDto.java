/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workdayoff.frame;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameName;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameNo;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento;

/**
 * The Class OvertimeWorkFrameFindDto.
 */
@Data
public class WorkdayoffFrameFindDto implements WorkdayoffFrameSetMemento {
	
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
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(String companyId) {
		//no coding
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameSetMemento#setUseClassification(nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr)
	 */
	@Override
	public void setUseClassification(NotUseAtr useAtr) {
		this.useAtr = useAtr.value;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setWorkdayoffFrameNo(nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameNo)
	 */
	@Override
	public void setWorkdayoffFrameNo(WorkdayoffFrameNo workdayoffFrNo) {
		this.workdayoffFrNo = workdayoffFrNo.v().intValue();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setTransferFrameName(nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameName)
	 */
	@Override
	public void setTransferFrameName(WorkdayoffFrameName transferFrName) {
		this.transferFrName = transferFrName.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setWorkdayoffFrameName(nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameName)
	 */
	@Override
	public void setWorkdayoffFrameName(WorkdayoffFrameName workdayoffFrName) {
		this.workdayoffFrName = workdayoffFrName.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameSetMemento#setRole(nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole)
	 */
	@Override
	public void setRole(WorkdayoffFrameRole role){
		this.role = role.value;
	}
}
