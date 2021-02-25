/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.ot.frame;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameName;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento;
import nts.uk.ctx.at.shared.dom.ot.frame.RoleOvertimeWork;

/**
 * The Class OvertimeWorkFrameFindDto.
 */
@Data
public class OvertimeWorkFrameFindDto implements OvertimeWorkFrameSetMemento {
	
	/** The overtime work fr no. */
	private int overtimeWorkFrNo;
	
	/** The overtime work fr name. */
	private String overtimeWorkFrName;
	
	/** The transfer fr name. */
	private String transferFrName;
	
	/** The use atr. */
	private int useAtr;
	
	/** The role. */
	private int role;
	
	/** The transfer atr. */
	private int transferAtr;
	
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
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento#setOvertimeWorkFrameNo(nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo)
	 */
	@Override
	public void setOvertimeWorkFrameNo(OvertimeWorkFrameNo overtimeWorkFrNo) {
		this.overtimeWorkFrNo = overtimeWorkFrNo.v().intValue();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento#setTransferFrameName(nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameName)
	 */
	@Override
	public void setTransferFrameName(OvertimeWorkFrameName transferFrName) {
		this.transferFrName = transferFrName.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameSetMemento#setOvertimeWorkFrameName(nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameName)
	 */
	@Override
	public void setOvertimeWorkFrameName(OvertimeWorkFrameName overtimeWorkFrName) {
		this.overtimeWorkFrName = overtimeWorkFrName.v();
	}

	@Override
	public void setRole(RoleOvertimeWork role) {
		this.role = role.value;
	}
	
	@Override
	public void setTransferAtr(NotUseAtr transferAtr) {
		this.transferAtr = transferAtr.value;
	}
}
