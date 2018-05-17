/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutputItemDailyWorkSchedule.
 */
// 日別勤務表の出力項目
@Getter
public class OutputItemDailyWorkSchedule extends AggregateRoot{
	
	/** The company ID. */
	// 会社ID
	private String companyID;
	
	/** The item code. */
	// コード
	private OutputItemSettingCode itemCode;
	
	/** The item name. */
	// 名称
	private OutputItemSettingName itemName;
	
	/** The lst displayed attendance. */
	// 表示する勤怠項目
	private List<AttendanceItemsDisplay> lstDisplayedAttendance;
	
	/** The lst remark content. */
	// 備考内容
	private List<PrintRemarksContent> lstRemarkContent;
	
	/** The zone name. */
	// 勤務種類・就業時間帯の名称
	private NameWorkTypeOrHourZone workTypeNameDisplay;
	
	/**
	 * Instantiates a new output item daily work schedule.
	 *
	 * @param memento the memento
	 */
	public OutputItemDailyWorkSchedule(OutputItemDailyWorkScheduleGetMemento memento) {
		this.companyID = memento.getCompanyID();
		this.itemCode = memento.getItemCode();
		this.itemName = memento.getItemName();
		this.lstDisplayedAttendance = memento.getLstDisplayedAttendance();
		this.lstRemarkContent = memento.getLstRemarkContent();
		this.workTypeNameDisplay = memento.getWorkTypeNameDisplay();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OutputItemDailyWorkScheduleSetMemento memento) {
		if (this.companyID == null) {
			this.companyID = AppContexts.user().companyId();
		}
		memento.setCompanyID(this.companyID);
		memento.setItemCode(this.itemCode);
		memento.setItemName(this.itemName);
		memento.setLstDisplayedAttendance(this.lstDisplayedAttendance);
		memento.setLstRemarkContent(this.lstRemarkContent);
		memento.setWorkTypeNameDisplay(this.workTypeNameDisplay);
	}
}
