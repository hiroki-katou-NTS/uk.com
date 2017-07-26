/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.work;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class WorkMonthlySetting.
 */
// 月間勤務就業設定

@Getter
public class WorkMonthlySetting extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The work type code. */
	// 勤務種類コード
	private WorkTypeCode workTypeCode;

	/** The working code. */
	// 就業時間帯コード
	private WorkingCode workingCode;
	
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
		this.workTypeCode = memento.getWorkTypeCode();
		this.workingCode = memento.getWorkingCode();
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
		memento.setWorkTypeCode(this.workTypeCode);
		memento.setWorkingCode(this.workingCode);
		memento.setDate(this.date);
		memento.setMonthlyPatternCode(this.monthlyPatternCode);
	}
}
