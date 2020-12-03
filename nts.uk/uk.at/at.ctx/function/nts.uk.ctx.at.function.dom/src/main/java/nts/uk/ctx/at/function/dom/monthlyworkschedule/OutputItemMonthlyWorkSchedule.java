/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutputItemMonthlyWorkSchedule.
 */
// 月別勤務表の出力項目
@Getter
@Setter
public class OutputItemMonthlyWorkSchedule extends AggregateRoot {

	/** The company ID. */
	// 会社ID
	private String companyID;
	
	/** The item code. */
	// コード
	private MonthlyOutputItemSettingCode itemCode;
	
	/** The item name. */
	// 名称
	private MonthlyOutputItemSettingName itemName;

	/** The lst displayed attendance. */
	// 表示する勤怠項目
	private List<MonthlyAttendanceItemsDisplay> lstDisplayedAttendance;

	/** The remark input no. */
	// 備考入力No
	private RemarkInputContent remarkInputNo;
	
	/** The remark input. */
	// 備考入力
	private boolean isRemarkPrinted;
	
	/** The Layout ID*/
	// 出力項目ID
	private String layoutID;
	
	/** The Employee ID. */
	// 社員ID
	private String employeeID;
	
	/** The text size */
	// 文字の大きさ
	private TextSizeCommonEnum textSize;
	
	/** The Item selection type*/
	// 項目選択種類
	private ItemSelectionEnum  itemType;
	
	
	/** The Constant MAX_ATTENDANCE_ITEM. */
	private static final String BIG_SIZE_MAX_ATTENDANCE_ITEM = "48";
	private static final String SMALL_SIZE_MAX_ATTENDANCE_ITEM = "60";

	/**
	 * Instantiates a new output item monthly work schedule.
	 *
	 * @param memento the memento
	 */
	public OutputItemMonthlyWorkSchedule(OutputItemMonthlyWorkScheduleGetMemento memento) {
		if (memento.getCompanyID() == null) {
			this.companyID = AppContexts.user().companyId();
		} else {
			this.companyID = memento.getCompanyID();
		}
		this.itemCode = memento.getItemCode();
		this.itemName = memento.getItemName();
		this.lstDisplayedAttendance = memento.getLstDisplayedAttendance();		
		this.remarkInputNo = memento.getRemarkInputNo();
		this.layoutID = memento.getLayoutID();
		this.employeeID = memento.getEmployeeID();
		this.textSize = memento.getTextSize();
		this.itemType = memento.getItemSelectionEnum();
		this.isRemarkPrinted = memento.getIsRemarkPrinted();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OutputItemMonthlyWorkScheduleSetMemento memento) {
		memento.setCompanyID(this.companyID == null ? AppContexts.user().companyId() : this.companyID);
		memento.setItemCode(this.itemCode);
		memento.setItemName(this.itemName);
		memento.setLayoutID(this.layoutID);
		memento.setLstDisplayedAttendance(this.lstDisplayedAttendance);
		memento.setRemarkInputNo(this.remarkInputNo);
		memento.setEmployeeID(this.employeeID);
		memento.setTextSize(this.textSize);
		memento.setItemSelectionEnum(this.itemType);
		memento.setIsRemarkPrinted(this.isRemarkPrinted);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		// execute algorithm アルゴリズム「登録チェック処理」を実行する to check C7_8 exist element?
		if (this.lstDisplayedAttendance.isEmpty() || this.lstDisplayedAttendance == null) {
			throw new BusinessException("Msg_880");
		}

		// check max display item 
		int numberDisplayItem = this.textSize == TextSizeCommonEnum.SMALL ? 60 : 48;
		
		// error message 
		String[] errString = this.textSize == TextSizeCommonEnum.SMALL
				? new String[] { SMALL_SIZE_MAX_ATTENDANCE_ITEM }
				: new String[] { BIG_SIZE_MAX_ATTENDANCE_ITEM };

		if (this.lstDisplayedAttendance.size() > numberDisplayItem) {
			throw new BusinessException("Msg_1297", errString);
		}
	}

	/**
	 * Instantiates a new output item monthly work schedule.
	 */
	public OutputItemMonthlyWorkSchedule() {
		super();
	}
	
}
