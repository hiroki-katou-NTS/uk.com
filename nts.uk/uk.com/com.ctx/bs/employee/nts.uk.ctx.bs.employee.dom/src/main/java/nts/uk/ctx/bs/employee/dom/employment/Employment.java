/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.employment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((salaryClosureId == null) ? 0 : salaryClosureId.hashCode());
		result = prime * result + ((workClosureId == null) ? 0 : workClosureId.hashCode());
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
		Employment other = (Employment) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (salaryClosureId == null) {
			if (other.salaryClosureId != null)
				return false;
		} else if (!salaryClosureId.equals(other.salaryClosureId))
			return false;
		if (workClosureId == null) {
			if (other.workClosureId != null)
				return false;
		} else if (!workClosureId.equals(other.workClosureId))
			return false;
		return true;
	}
	
	
}
