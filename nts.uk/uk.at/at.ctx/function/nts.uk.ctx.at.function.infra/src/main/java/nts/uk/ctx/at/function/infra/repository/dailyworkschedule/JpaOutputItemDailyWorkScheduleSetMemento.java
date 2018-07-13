/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.dailyworkschedule.NameWorkTypeOrHourZone;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtAttendanceDisplay;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtAttendanceDisplayPK;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtItemWorkSchedule;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtItemWorkSchedulePK;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtPrintRemarkCont;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtPrintRemarkContPK;

/**
 * The Class JpaOutputItemDailyWorkScheduleSetMemento.
 * @author HoangDD
 */
public class JpaOutputItemDailyWorkScheduleSetMemento implements OutputItemDailyWorkScheduleSetMemento{

	/** The kfnmt item work schedule. */
	private KfnmtItemWorkSchedule kfnmtItemWorkSchedule;
	
	/**
	 * Instantiates a new jpa output item daily work schedule set memento.
	 */
	public JpaOutputItemDailyWorkScheduleSetMemento(KfnmtItemWorkSchedule entity) {
		if (entity.getId() == null) {
			KfnmtItemWorkSchedulePK key = new KfnmtItemWorkSchedulePK();
			entity.setId(key);	
		}
		this.kfnmtItemWorkSchedule = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String companyID) {
		KfnmtItemWorkSchedulePK key = kfnmtItemWorkSchedule.getId();
		key.setCid(companyID);
		kfnmtItemWorkSchedule.setId(key);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento#setItemCode(nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode)
	 */
	@Override
	public void setItemCode(OutputItemSettingCode itemCode) {
		KfnmtItemWorkSchedulePK key = kfnmtItemWorkSchedule.getId();
		key.setItemCode(itemCode.v());
		kfnmtItemWorkSchedule.setId(key);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento#setItemName(nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName)
	 */
	@Override
	public void setItemName(OutputItemSettingName itemName) {
		kfnmtItemWorkSchedule.setItemName(itemName.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento#setLstDisplayedAttendance(java.util.List)
	 */
	@Override
	public void setLstDisplayedAttendance(List<AttendanceItemsDisplay> lstDisplayAttendance) {
		List<KfnmtAttendanceDisplay> lstKfnmtAttendanceDisplay
							= lstDisplayAttendance.stream().map(obj -> {
																KfnmtAttendanceDisplay entity = new KfnmtAttendanceDisplay();
																KfnmtAttendanceDisplayPK key = new KfnmtAttendanceDisplayPK();
																key.setCid(this.kfnmtItemWorkSchedule.getId().getCid());
																key.setItemCode(this.kfnmtItemWorkSchedule.getId().getItemCode());
																key.setOrderNo(obj.getOrderNo());
																entity.setId(key);
																entity.setAtdDisplay(new BigDecimal(obj.getAttendanceDisplay()));
																return entity;
															}).collect(Collectors.toList());
		kfnmtItemWorkSchedule.setLstKfnmtAttendanceDisplay(lstKfnmtAttendanceDisplay);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento#setLstRemarkContent(java.util.List)
	 */
	@Override
	public void setLstRemarkContent(List<PrintRemarksContent> lstRemarkContent) {
		List<KfnmtPrintRemarkCont> lstKfnmtAttendanceDisplay
							= lstRemarkContent.stream().map(obj -> {
																KfnmtPrintRemarkCont entity = new KfnmtPrintRemarkCont();
																KfnmtPrintRemarkContPK key = new KfnmtPrintRemarkContPK();
																key.setCid(this.kfnmtItemWorkSchedule.getId().getCid());
																key.setItemCode(this.kfnmtItemWorkSchedule.getId().getItemCode());
																key.setPrintItem(obj.getPrintItem().value);
																entity.setId(key);
																entity.setUseCls(new BigDecimal(obj.isUsedClassification() ? 1 : 0));
																return entity;
															}).collect(Collectors.toList());
		kfnmtItemWorkSchedule.setLstKfnmtPrintRemarkCont(lstKfnmtAttendanceDisplay);		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento#setWorkTypeNameDisplay(nts.uk.ctx.at.function.dom.dailyworkschedule.NameWorkTypeOrHourZone)
	 */
	@Override
	public void setWorkTypeNameDisplay(NameWorkTypeOrHourZone workTypeNameDisplay) {
		kfnmtItemWorkSchedule.setWorkTypeNameDisplay(new BigDecimal(workTypeNameDisplay.value));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento#setRemarkInputNo(java.lang.Integer)
	 */
	@Override
	public void setRemarkInputNo(RemarkInputContent remarkInputNo) {
		kfnmtItemWorkSchedule.setRemarkInputNo(BigDecimal.valueOf(remarkInputNo.value));
	}
}
