/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.work;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
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
	
	/** The ymdk. */
	// 年月日
	private BigDecimal ymdk;
	
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
		this.ymdk = memento.getYmdK();
		this.monthlyPatternCode = memento.getMonthlyPatternCode();
	}

	/**
	 * Instantiates a new work monthly setting.
	 *
	 * @param ymdk the ymdk
	 * @param monthlyPatternCode the monthly pattern code
	 */
	public WorkMonthlySetting(BigDecimal ymdk, String monthlyPatternCode) {
		this.workTypeCode = new WorkTypeCode("");
		this.workingCode = new WorkingCode("");
		this.ymdk = ymdk;
		this.monthlyPatternCode = new MonthlyPatternCode(monthlyPatternCode);
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
		memento.setYmdK(this.ymdk);
		memento.setMonthlyPatternCode(this.monthlyPatternCode);
	}
}
