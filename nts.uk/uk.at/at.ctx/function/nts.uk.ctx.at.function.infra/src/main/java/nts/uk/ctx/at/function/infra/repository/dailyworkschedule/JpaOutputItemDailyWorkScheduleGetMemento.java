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
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtItemWorkSchedule;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtItemWorkSchedulePK;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaOutputItemDailyWorkScheduleGetMemento.
 * @author HoangDD
 */
public class JpaOutputItemDailyWorkScheduleGetMemento implements OutputItemDailyWorkScheduleGetMemento{

	/** The kfnmt item work schedule. */
	private KfnmtItemWorkSchedule kfnmtItemWorkSchedule;
	
	/**
	 * Instantiates a new jpa output item daily work schedule get memento.
	 */
	public JpaOutputItemDailyWorkScheduleGetMemento(KfnmtItemWorkSchedule entity) {
		this.kfnmtItemWorkSchedule = entity;
		if (entity.getId() == null) {
			KfnmtItemWorkSchedulePK key = new KfnmtItemWorkSchedulePK();
			key.setCid(AppContexts.user().companyId());
			entity.setId(key);
		}
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		return this.kfnmtItemWorkSchedule.getId().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getItemCode()
	 */
	@Override
	public OutputItemSettingCode getItemCode() {
		return new OutputItemSettingCode(this.kfnmtItemWorkSchedule.getId().getItemCode());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getItemName()
	 */
	@Override
	public OutputItemSettingName getItemName() {
		return new OutputItemSettingName(this.kfnmtItemWorkSchedule.getItemName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getLstDisplayedAttendance()
	 */
	@Override
	public List<AttendanceItemsDisplay> getLstDisplayedAttendance() {
		return this.kfnmtItemWorkSchedule.getLstKfnmtAttendanceDisplay().stream()
									.map(entity -> {
										return new AttendanceItemsDisplay((int) entity.getId().getOrderNo(), entity.getAtdDisplay().intValue());
									}).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getLstRemarkContent()
	 */
	@Override
	public List<PrintRemarksContent> getLstRemarkContent() {
		return this.kfnmtItemWorkSchedule.getLstKfnmtPrintRemarkCont().stream()
									.map(entity -> {
										return new PrintRemarksContent(entity.getUseCls().intValue(), (int) entity.getId().getPrintItem());
									}).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getWorkTypeNameDisplay()
	 */
	@Override
	public NameWorkTypeOrHourZone getWorkTypeNameDisplay() {
		return NameWorkTypeOrHourZone.valueOf(this.kfnmtItemWorkSchedule.getWorkTypeNameDisplay().intValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento#getRemarkInputNo()
	 */
	@Override
	public RemarkInputContent getRemarkInputNo() {
		return RemarkInputContent.valueOf(this.kfnmtItemWorkSchedule.getRemarkInputNo().intValue());
	}
}
