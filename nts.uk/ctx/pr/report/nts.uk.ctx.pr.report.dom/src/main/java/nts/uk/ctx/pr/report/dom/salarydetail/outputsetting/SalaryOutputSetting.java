/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.outputsetting;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Class SalaryOutputSetting.
 */
@Getter
public class SalaryOutputSetting {

	/** The company code. */
	private CompanyCode companyCode;

	/** The code. */
	private SalaryOutputSettingCode code;

	/** The name. */
	private SalaryOutputSettingName name;

	/** The category settings. */
	private List<SalaryCategorySetting> categorySettings;

	/**
	 * Instantiates a new salary output setting.
	 *
	 * @param memento the memento
	 */
	public SalaryOutputSetting(SalaryOutputSettingGetMemento memento) {
		super();
		this.companyCode = memento.getCompanyCode();
		this.code = memento.getCode();
		this.name = memento.getName();
		this.categorySettings = memento.getCategorySettings();

	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(SalaryOutputSettingSetMemento memento) {
		memento.setCode(this.code);
		memento.setName(this.name);
		memento.setCompanyCode(this.companyCode);
		memento.setCategorySettings(this.categorySettings);
	}
}
