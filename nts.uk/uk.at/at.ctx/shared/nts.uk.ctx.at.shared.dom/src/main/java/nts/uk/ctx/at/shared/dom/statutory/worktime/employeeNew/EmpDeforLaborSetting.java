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
 * The Class EmpDeforLaborSetting.
 */
@Getter
// 社員別変形労働月間労働時間
public class EmpDeforLaborSetting extends AggregateRoot implements MonthStatutoryWorkingHourDeforWorker{

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;
	
	/** The employee id. */
	/** 社員ID. */
	private EmployeeId employeeId;
	
	/** The year. */
	/** 年. */
	private Year year;
	
	/** The statutory setting. */
	/** 法定時間. */
	private List<MonthlyTime> statutorySetting;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.MonthStatutoryWorkingHourDeforWorker#getListStatutorySetting()
	 */
	@Override
	public List<MonthlyTime> getListStatutorySetting() {
		return statutorySetting;
	}
	
	/**
	 * Instantiates a new emp defor labor setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmpDeforLaborSetting(EmpDeforLaborSetting memento) {
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
	public void saveToMemento(EmpDeforLaborSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setYear(this.year);
		memento.setStatutorySetting(this.statutorySetting);
	}
	
	
}
