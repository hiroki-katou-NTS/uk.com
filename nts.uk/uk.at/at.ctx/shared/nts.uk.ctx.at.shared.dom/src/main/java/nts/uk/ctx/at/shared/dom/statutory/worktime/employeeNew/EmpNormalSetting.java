/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.MonthlyTime;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourDeforWorker;

/**
 * The Class EmpNormalSetting.
 */
@Getter
// 社員別通常勤務月間労働時間
public class EmpNormalSetting extends AggregateRoot implements MonthStatutoryWorkingHourDeforWorker{

	/** The company id. */
	private CompanyId companyId;
	
	/** The employee id. */
	private EmployeeId employeeId;
	
	/** The year. */
	private Year year;
	
	/** The statutory setting. */
	private List<MonthlyTime> statutorySetting;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourDeforWorker#getListStatutorySetting()
	 */
	@Override
	public List<MonthlyTime> getListStatutorySetting() {
		return statutorySetting;
	}
	
	/**
	 * Instantiates a new emp normal setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmpNormalSetting(EmpNormalSetting memento) {
		this.companyId = memento.getCompanyId();
		this.employeeId = memento.getEmployeeId();
		this.year = memento.getYear();
		this.statutorySetting = memento.getStatutorySetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmpNormalSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setYear(this.year);
		memento.setStatutorySetting(this.statutorySetting);
	}
}
