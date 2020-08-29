/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.math.BigDecimal;
import java.util.List;

import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.dailyworkschedule.NameWorkTypeOrHourZone;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutItem;

/**
 * The Class JpaOutputItemDailyWorkScheduleSetMemento.
 * @author HoangDD
 */
public class JpaOutputItemDailyWorkScheduleSetMemento implements OutputItemDailyWorkScheduleSetMemento {

	/** The kfnmt item work schedule. */
	private KfnmtRptWkDaiOutItem kfnmtRptWkDaiOutItem;

	/**
	 * Instantiates a new jpa output item daily work schedule set memento.
	 */
	public JpaOutputItemDailyWorkScheduleSetMemento(KfnmtRptWkDaiOutItem entity) {
		this.kfnmtRptWkDaiOutItem = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento#setItemCode(nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode)
	 */
	@Override
	public void setItemCode(OutputItemSettingCode itemCode) {
		this.kfnmtRptWkDaiOutItem.setItemCode(itemCode.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento#setItemName(nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName)
	 */
	@Override
	public void setItemName(OutputItemSettingName itemName) {
		this.kfnmtRptWkDaiOutItem.setItemName(itemName.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento#setLstDisplayedAttendance(java.util.List)
	 */
	@Override
	public void setLstDisplayedAttendance(List<AttendanceItemsDisplay> lstDisplayAttendance) {
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento#setLstRemarkContent(java.util.List)
	 */
	@Override
	public void setLstRemarkContent(List<PrintRemarksContent> lstRemarkContent) {
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento#setWorkTypeNameDisplay(nts.uk.ctx.at.function.dom.dailyworkschedule.NameWorkTypeOrHourZone)
	 */
	@Override
	public void setWorkTypeNameDisplay(NameWorkTypeOrHourZone workTypeNameDisplay) {
		this.kfnmtRptWkDaiOutItem.setWorkTypeNameDisplay(new BigDecimal(workTypeNameDisplay.value));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento#setRemarkInputNo(java.lang.Integer)
	 */
	@Override
	public void setRemarkInputNo(RemarkInputContent remarkInputNo) {
		this.kfnmtRptWkDaiOutItem.setNoteInputNo(BigDecimal.valueOf(remarkInputNo.value));
	}
}
