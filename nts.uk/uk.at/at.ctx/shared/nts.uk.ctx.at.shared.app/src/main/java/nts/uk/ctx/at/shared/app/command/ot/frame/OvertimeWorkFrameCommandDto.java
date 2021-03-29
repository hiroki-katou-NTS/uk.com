/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.frame;

import java.math.BigDecimal;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameGetMemento;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameName;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.ot.frame.RoleOvertimeWork;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OvertimeWorkFrameCommandDto.
 */
@Data

public class OvertimeWorkFrameCommandDto implements OvertimeWorkFrameGetMemento {
	
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
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameGetMemento#getOvertimeWorkFrameNo()
	 */
	@Override
	public OvertimeWorkFrameNo getOvertimeWorkFrameNo() {
		return new OvertimeWorkFrameNo(BigDecimal.valueOf(this.overtimeWorkFrNo));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameGetMemento#getTransferFrameName()
	 */
	@Override
	public OvertimeWorkFrameName getTransferFrameName() {
		return new OvertimeWorkFrameName(this.transferFrName);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameGetMemento#getOvertimeWorkFrameName()
	 */
	@Override
	public OvertimeWorkFrameName getOvertimeWorkFrameName() {
		return new OvertimeWorkFrameName(this.overtimeWorkFrName);
	}
	
	@Override
	public RoleOvertimeWork getRole() {
		return RoleOvertimeWork.valueOf(this.role);
	}
	
	@Override
	public NotUseAtr getTransferAtr() {
		return NotUseAtr.valueOf(this.transferAtr);
	}
}
