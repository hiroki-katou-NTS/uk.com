/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.MonthlyTime;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourDeforWorker;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmpMentDeforLaborSetting.
 */
@Getter
//雇用別変形労働月間労働時間.
public class EmpMentDeforLaborSetting extends AggregateRoot implements MonthStatutoryWorkingHourDeforWorker{
	
	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;
	
	/** The employee code. */
	/** 社員ID. */
	private EmploymentCode employeeCode;
	
	/** The year. */
	/** 年. */
	private Year year;
	
	/** The statutory setting. */
	/** 法定時間. */
	private List<MonthlyTime> statutorySetting;
	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.StatutoryWorkTimeSettingNew#getWorkingTimeSettingNew()
	 */
	@Override
	public List<MonthlyTime> getListStatutorySetting() {
		return statutorySetting;
	}
	
	/**
	 * Instantiates a new emp ment defor labor setting.
	 *
	 * @param memento the memento
	 */
	public EmpMentDeforLaborSetting (EmpMentDeforLaborSetting memento) {
		this.companyId  = memento.getCompanyId();
		this.employeeCode = memento.getEmployeeCode();
		this.year = memento.getYear();
		this.statutorySetting = memento.getStatutorySetting();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (EmpMentDeforLaborSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employeeCode);
		memento.setYear(this.year);
		memento.setStatutorySetting(this.statutorySetting);
	}
}
