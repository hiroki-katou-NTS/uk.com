/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class OutputItemDailyWorkSchedule.
 * @author HoangDD
 */
// 日別勤務表の出力項目
@Getter
public class OutputItemDailyWorkSchedule extends DomainObject {

	/**
	 * 出力レイアウトID
	 */
	private String outputLayoutId;
	
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
	
	/** The remark input no. */
	// 備考入力No
	private RemarkInputContent remarkInputNo;

	// 文字の大きさ
	private FontSizeEnum fontSize;
	
	private static final String MAX_ATTENDANCE_ITEM = "48";

	/**
	 * Instantiates a new output item daily work schedule.
	 *
	 * @param memento the memento
	 */
	public OutputItemDailyWorkSchedule(OutputItemDailyWorkScheduleGetMemento memento) {
		this.itemCode = memento.getItemCode();
		this.itemName = memento.getItemName();
		this.lstDisplayedAttendance = memento.getLstDisplayedAttendance();
		this.lstRemarkContent = memento.getLstRemarkContent();
		this.workTypeNameDisplay = memento.getWorkTypeNameDisplay();
		this.remarkInputNo = memento.getRemarkInputNo();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OutputItemDailyWorkScheduleSetMemento memento) {

		memento.setItemCode(this.itemCode);
		memento.setItemName(this.itemName);
		memento.setLstDisplayedAttendance(this.lstDisplayedAttendance);
		memento.setLstRemarkContent(this.lstRemarkContent);
		memento.setWorkTypeNameDisplay(this.workTypeNameDisplay);
		memento.setRemarkInputNo(this.remarkInputNo);
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		// execute algorithm アルゴリズム「登録チェック処理」を実行する to check C7_8 exist element?
		if (this.lstDisplayedAttendance.isEmpty() || this.lstDisplayedAttendance == null) {
			throw new BusinessException("Msg_880");
		}
		
		if (this.lstDisplayedAttendance.size() > 48) {
			throw new BusinessException("Msg_1297", new String[]{MAX_ATTENDANCE_ITEM});
		}
	}
}
