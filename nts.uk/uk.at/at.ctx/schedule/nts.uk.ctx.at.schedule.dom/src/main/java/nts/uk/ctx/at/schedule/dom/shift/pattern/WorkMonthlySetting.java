/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.worktime.SiftCode;

/**
 * The Class WorkMonthlySetting.
 */
// 月間勤務就業設定

/**
 * Gets the sift code.
 *
 * @return the sift code
 */
@Getter
public class WorkMonthlySetting extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The work monthly setting code. */
	// 勤務種類設定コード
	private WorkMonthlySettingCode workMonthlySettingCode;

	/** The sift code. */
	// 就業時間帯コード
	private SiftCode siftCode;
	
	
	/** The date. */
	// 年月日
	private GeneralDate date;
	
	
	/** The monthly pattern code. */
	// 月間パターンコード
	private MonthlyPatternCode monthlyPatternCode;
	

	/**
	 * Instantiates a new work monthly setting.
	 *
	 * @param memento the memento
	 */
	public WorkMonthlySetting(WorkMonthlySettingGetMemento memento){
		this.companyId = memento.getCompanyId();
		this.workMonthlySettingCode = memento.getWorkMonthlySettingCode();
		this.siftCode = memento.getSiftCode();
		this.date = memento.getDate();
		this.monthlyPatternCode = memento.getMonthlyPatternCode();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkMonthlySettingSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setWorkMonthlySettingCode(this.workMonthlySettingCode);
		memento.setSiftCode(this.siftCode);
		memento.setDate(this.date);
		memento.setMonthlyPatternCode(this.monthlyPatternCode);
	}
}
