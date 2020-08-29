/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.dailyworkschedule.NameWorkTypeOrHourZone;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutItem;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutatd;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutnote;

/**
 * The Class JpaOutputItemDailyWorkScheduleGetMemento.
 * @author HoangDD
 */
public class JpaOutputItemDailyWorkScheduleGetMemento implements OutputItemDailyWorkScheduleGetMemento {

	/** The kfnmt item work schedule. */
	private KfnmtRptWkDaiOutItem kfnmtRptWkDaiOutItem;
	
	private List<KfnmtRptWkDaiOutatd> kfnmtRptWkDaiOutatds;
	
	private List<KfnmtRptWkDaiOutnote> kfnmtRptWkDaiOutnotes;
	
	/**
	 * Instantiates a new jpa output item daily work schedule get memento.
	 */
	public JpaOutputItemDailyWorkScheduleGetMemento(KfnmtRptWkDaiOutItem entity
			, List<KfnmtRptWkDaiOutatd> kfnmtRptWkDaiOutatds
			, List<KfnmtRptWkDaiOutnote> kfnmtRptWkDaiOutnotes) {
		this.kfnmtRptWkDaiOutItem = entity;
		this.kfnmtRptWkDaiOutatds = kfnmtRptWkDaiOutatds;
		this.kfnmtRptWkDaiOutnotes = kfnmtRptWkDaiOutnotes;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getItemCode()
	 */
	@Override
	public OutputItemSettingCode getItemCode() {
		return new OutputItemSettingCode(this.kfnmtRptWkDaiOutItem.getItemCode());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getItemName()
	 */
	@Override
	public OutputItemSettingName getItemName() {
		return new OutputItemSettingName(this.kfnmtRptWkDaiOutItem.getItemName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getLstDisplayedAttendance()
	 */
	@Override
	public List<AttendanceItemsDisplay> getLstDisplayedAttendance() {
		return this.kfnmtRptWkDaiOutatds.stream()
									.map(entity -> {
										return new AttendanceItemsDisplay((int) entity.getId().getOrderNo(), entity.getAtdDisplay().intValue());
									}).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getLstRemarkContent()
	 */
	@Override
	public List<PrintRemarksContent> getLstRemarkContent() {
		return this.kfnmtRptWkDaiOutnotes.stream()
									.map(entity -> {
										return new PrintRemarksContent(entity.getUseCls().intValue(), (int) entity.getId().getPrintItem());
									}).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getWorkTypeNameDisplay()
	 */
	@Override
	public NameWorkTypeOrHourZone getWorkTypeNameDisplay() {
		return NameWorkTypeOrHourZone.valueOf(this.kfnmtRptWkDaiOutItem.getWorkTypeNameDisplay().intValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getRemarkInputNo()
	 */
	@Override
	public RemarkInputContent getRemarkInputNo() {
		return RemarkInputContent.valueOf(this.kfnmtRptWkDaiOutItem.getNoteInputNo().intValue());
	}
}
