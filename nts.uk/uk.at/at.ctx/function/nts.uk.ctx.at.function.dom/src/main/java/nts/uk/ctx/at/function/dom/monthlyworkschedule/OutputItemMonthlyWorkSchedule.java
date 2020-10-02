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

	/** The is print. */
	// 備考欄の印字設定
	private PrintSettingRemarksColumn printSettingRemarksColumn;

	/** The remark input no. */
	// 備考入力No
	private RemarkInputContent remarkInputNo;
	
	/** The remark input. */
	// 備考入力
	private boolean remarkInput;
	
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
	
	/** The Contract_CD*/
	// 項目選択種類
	private String contractCD;
	
	/** The Constant MAX_ATTENDANCE_ITEM. */
	private static final int MAX_ATTENDANCE_ITEM = 48;

	/**
	 * Instantiates a new output item monthly work schedule.
	 *
	 * @param memento
	 *            the memento
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
		this.printSettingRemarksColumn = memento.getPrintSettingRemarksColumn();
		this.remarkInputNo = memento.getRemarkInputNo();
		this.layoutID = memento.getLayoutID();
		this.employeeID = memento.getEmployeeID();
		this.textSize = memento.getTextSize();
		this.itemType = memento.getItemSelectionEnum();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(OutputItemMonthlyWorkScheduleSetMemento memento) {
		if (this.companyID == null) {
			this.companyID = AppContexts.user().companyId();
		}
		memento.setCompanyID(this.companyID);
		memento.setItemCode(this.itemCode);
		memento.setItemName(this.itemName);
		memento.setLstDisplayedAttendance(this.lstDisplayedAttendance);
		memento.setPrintRemarksColumn(this.printSettingRemarksColumn);
		memento.setRemarkInputNo(this.remarkInputNo);
		memento.setLayoutID(this.layoutID);
		memento.setEmployeeID(this.employeeID);
		memento.setTextSize(this.textSize);
		memento.setItemSelectionEnum(this.itemType);
		memento.setContractCD(AppContexts.user().contractCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
		// execute algorithm アルゴリズム「登録チェック処理」を実行する to check C7_8 exist element?
		if (this.lstDisplayedAttendance.isEmpty() || this.lstDisplayedAttendance == null) {
			throw new BusinessException("Msg_880");
		}
		
		if (this.lstDisplayedAttendance.size() > MAX_ATTENDANCE_ITEM) {
			throw new BusinessException("Msg_1297", String.valueOf(MAX_ATTENDANCE_ITEM));
		}
	}

	/**
	 * Instantiates a new output item monthly work schedule.
	 */
	public OutputItemMonthlyWorkSchedule() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyID == null) ? 0 : companyID.hashCode());
		result = prime * result + ((employeeID == null) ? 0 : employeeID.hashCode());
		result = prime * result + ((itemCode == null) ? 0 : itemCode.hashCode());
		result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + ((itemType == null) ? 0 : itemType.hashCode());
		result = prime * result + ((layoutID == null) ? 0 : layoutID.hashCode());
		result = prime * result + ((lstDisplayedAttendance == null) ? 0 : lstDisplayedAttendance.hashCode());
		result = prime * result + ((printSettingRemarksColumn == null) ? 0 : printSettingRemarksColumn.hashCode());
		result = prime * result + (remarkInput ? 1231 : 1237);
		result = prime * result + ((remarkInputNo == null) ? 0 : remarkInputNo.hashCode());
		result = prime * result + ((textSize == null) ? 0 : textSize.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OutputItemMonthlyWorkSchedule other = (OutputItemMonthlyWorkSchedule) obj;
		if (companyID == null) {
			if (other.companyID != null)
				return false;
		} else if (!companyID.equals(other.companyID))
			return false;
		if (employeeID == null) {
			if (other.employeeID != null)
				return false;
		} else if (!employeeID.equals(other.employeeID))
			return false;
		if (itemCode == null) {
			if (other.itemCode != null)
				return false;
		} else if (!itemCode.equals(other.itemCode))
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (itemType != other.itemType)
			return false;
		if (layoutID == null) {
			if (other.layoutID != null)
				return false;
		} else if (!layoutID.equals(other.layoutID))
			return false;
		if (lstDisplayedAttendance == null) {
			if (other.lstDisplayedAttendance != null)
				return false;
		} else if (!lstDisplayedAttendance.equals(other.lstDisplayedAttendance))
			return false;
		if (printSettingRemarksColumn != other.printSettingRemarksColumn)
			return false;
		if (remarkInput != other.remarkInput)
			return false;
		if (remarkInputNo != other.remarkInputNo)
			return false;
		if (textSize != other.textSize)
			return false;
		return true;
	}
	
	
}
