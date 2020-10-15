/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.work;

import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.StringUtils;

/**
 * The Class WorkMonthlySetting.
 */
// 月間パターンの勤務情報
@Getter
@Setter
public class WorkMonthlySetting extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private final CompanyId companyId;

	//	勤務情報
	private WorkInformation workInformation;
	
	/** The ymdk. */
	// 年月日
	private final GeneralDate ymdk;
	
	/** The monthly pattern code. */
	// 月間パターンコード
	private final MonthlyPatternCode monthlyPatternCode;
	

	/**
	 * Instantiates a new work monthly setting.
	 *
	 * @param memento the memento
	 */
	public WorkMonthlySetting(WorkMonthlySettingGetMemento memento){
		this.companyId = memento.getCompanyId();
		this.workInformation = new WorkInformation(memento.getWorkTypeCode().v(),memento.getWorkingCode().v()) ;
		this.ymdk = memento.getYmdK();
		this.monthlyPatternCode = memento.getMonthlyPatternCode();
	}

	/**
	 * Instantiates a new work monthly setting.
	 *
	 * @param ymdk the ymdk
	 * @param monthlyPatternCode the monthly pattern code
	 */
	public WorkMonthlySetting(GeneralDate ymdk, String monthlyPatternCode) {
		this.companyId = new CompanyId(AppContexts.user().companyId());
		this.workInformation = new WorkInformation("", "");
		this.ymdk = ymdk;
		this.monthlyPatternCode = new MonthlyPatternCode(monthlyPatternCode);
	}

	/**
	 * Instantiates a new work monthly setting.
	 *	[C-0] 月間パターンの勤務情報(会社ID, 月間パターンコード, 年月日, 勤務情報)
	 *
	 * @param companyId the companyId
	 * @param workMonthlyId the workMonthlyId
	 * @param date the date
	 * @param workInformation the workInformation
	 */
	public WorkMonthlySetting(String companyId, String workMonthlyId, GeneralDate date, WorkInformation workInformation ) {
		this.companyId = new CompanyId(companyId);
		this.workInformation = workInformation;
		this.monthlyPatternCode = new MonthlyPatternCode(workMonthlyId);
		this.ymdk = date;
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkMonthlySettingSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setWorkTypeCode(this.workInformation.getWorkTypeCode());
		memento.setWorkingCode(this.workInformation.getWorkTimeCode() == null || StringUtils.isEmpty(this.workInformation.getWorkTimeCode().v()) ? null : this.workInformation.getWorkTimeCode());
		memento.setYmdK(this.ymdk);
		memento.setMonthlyPatternCode(this.monthlyPatternCode);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result
				+ ((monthlyPatternCode == null) ? 0 : monthlyPatternCode.hashCode());
		result = prime * result + ((ymdk == null) ? 0 : ymdk.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkMonthlySetting other = (WorkMonthlySetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (monthlyPatternCode == null) {
			if (other.monthlyPatternCode != null)
				return false;
		} else if (!monthlyPatternCode.equals(other.monthlyPatternCode))
			return false;
		if (ymdk == null) {
			if (other.ymdk != null)
				return false;
		} else if (!ymdk.equals(other.ymdk))
			return false;
		return true;
	}

	/**
	 * [1] エラーチェックする
	 *
	 * @param require
	 * @return
	 */
	public void checkForErrors(WorkInformation.Require require) {
		ErrorStatusWorkInfo errorStatusWorkInfo = this.workInformation.checkErrorCondition(require);
		switch (errorStatusWorkInfo){
			case WORKTYPE_WAS_DELETE:
				throw new BusinessException("Msg_1608");
			case WORKTIME_WAS_DELETE:
				throw new BusinessException("Msg_1609");
			case WORKTIME_ARE_REQUIRE_NOT_SET:
				throw new BusinessException("Msg_435");
			case WORKTIME_ARE_SET_WHEN_UNNECESSARY:
				throw new BusinessException("Msg_434");
		}
	}
	
}
