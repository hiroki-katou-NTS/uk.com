/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * 雇用.
 */
@Getter
public class Employment extends AggregateRoot {
	
	/** 会社ID. */
	private CompanyId companyId;
	
	/** 就業締めID. */
	private Integer workClosureId;
	
	/** 給与締めID. */
	private Integer salaryClosureId;
	
	/** 雇用コード. */
	private EmploymentCode employmentCode;
	
	/** 雇用名称. */
	private EmploymentName employmentName;
	
	/**
	 * Instantiates a new employment.
	 *
	 * @param memento the memento
	 */
	public Employment(EmploymentGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employmentCode = memento.getEmploymentCode();
		this.employmentName = memento.getEmploymentName();
		this.workClosureId = memento.getWorkClosureId();
		this.salaryClosureId = memento.getSalaryClosureId();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EmploymentSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employmentCode);
		memento.setEmploymentName(this.employmentName);
		memento.setWorkClosureId(this.workClosureId);
		memento.setSalaryClosureId(this.salaryClosureId);
	}
	
}
