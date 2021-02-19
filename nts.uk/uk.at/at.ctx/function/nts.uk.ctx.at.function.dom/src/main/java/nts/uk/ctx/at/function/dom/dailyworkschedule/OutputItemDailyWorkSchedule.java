/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.gul.text.StringUtil;

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
	private String layoutId;
	
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
	
	private static final String MAX_ATTENDANCE_ITEM_BIG_SIZE = "48";
	private static final String MAX_ATTENDANCE_ITEM_SMALL_SIZE = "60";

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
		this.layoutId = memento.getLayoutId();
		this.fontSize = memento.getFontSize();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OutputItemDailyWorkScheduleSetMemento memento) {
		if (!StringUtil.isNullOrEmpty(this.layoutId, true)) {
			memento.setLayoutId(this.layoutId);
		}
		memento.setItemCode(this.itemCode);
		memento.setItemName(this.itemName);
		memento.setLstDisplayedAttendance(this.lstDisplayedAttendance);
		memento.setLstRemarkContent(this.lstRemarkContent);
		memento.setWorkTypeNameDisplay(this.workTypeNameDisplay);
		memento.setRemarkInputNo(this.remarkInputNo);
		memento.setFontSize(this.fontSize);
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		// execute algorithm アルゴリズム「登録チェック処理」を実行する to check C7_8 exist element?
		if ( this.lstDisplayedAttendance == null || this.lstDisplayedAttendance.isEmpty()) {
			throw new BusinessException("Msg_880");
		}

		// check max display item 
		int numberDisplayItem = fontSize == FontSizeEnum.SMALL ? 60 : 48;
		
		// error message 
		String[] errString = fontSize == FontSizeEnum.SMALL
				? new String[] { MAX_ATTENDANCE_ITEM_SMALL_SIZE }
				: new String[] { MAX_ATTENDANCE_ITEM_BIG_SIZE };

		if (this.lstDisplayedAttendance.size() > numberDisplayItem) {
			throw new BusinessException("Msg_1297", errString);
		}
	}
}
