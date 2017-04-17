/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.printsetting;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryOutputDistinction;

/**
 * The Class SalaryPrintSetting.
 */
@Getter
public class SalaryPrintSetting extends DomainObject {

	/** The company code. */
	private String companyCode;

	/** The output distinction. */
	private SalaryOutputDistinction outputDistinction;

	/** The show department monthly amount. */
	private Boolean showDepartmentMonthlyAmount;

	/** The show detail. */
	private Boolean showDetail;

	/** The show division monthly total. */
	private Boolean showDivisionMonthlyTotal;

	/** The show division total. */
	private Boolean showDivisionTotal;

	/** The show hierarchy 1. */
	private Boolean showHierarchy1;

	/** The show hierarchy 2. */
	private Boolean showHierarchy2;

	/** The show hierarchy 3. */
	private Boolean showHierarchy3;

	/** The show hierarchy 4. */
	private Boolean showHierarchy4;

	/** The show hierarchy 5. */
	private Boolean showHierarchy5;

	/** The show hierarchy 6. */
	private Boolean showHierarchy6;

	/** The show hierarchy 7. */
	private Boolean showHierarchy7;

	/** The show hierarchy 8. */
	private Boolean showHierarchy8;

	/** The show hierarchy 9. */
	private Boolean showHierarchy9;

	/** The show hierarchy accumulation. */
	private Boolean showHierarchyAccumulation;

	/** The show hierarchy monthly accumulation. */
	private Boolean showHierarchyMonthlyAccumulation;

	/** The show monthly amount. */
	private Boolean showMonthlyAmount;

	/** The show personal monthly amount. */
	private Boolean showPersonalMonthlyAmount;

	/** The show personal total. */
	private Boolean showPersonalTotal;

	/** The show sectional calculation. */
	private Boolean showSectionalCalculation;

	/** The show total. */
	private Boolean showTotal;

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new unit price history.
	 *
	 * @param memento
	 *            the memento
	 */
	public SalaryPrintSetting(SalaryPrintSettingGetMemento memento) {
		this.companyCode = memento.getCompanyCode();
		this.outputDistinction = memento.getOutputDistinction();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(SalaryPrintSettingSetMemento memento) {
		memento.setCompanyCode(this.companyCode);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyCode == null) ? 0 : companyCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalaryPrintSetting other = (SalaryPrintSetting) obj;
		if (companyCode == null) {
			if (other.companyCode != null)
				return false;
		} else if (!companyCode.equals(other.companyCode))
			return false;
		return true;
	}
}
