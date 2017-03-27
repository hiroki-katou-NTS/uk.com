package nts.uk.ctx.pr.report.app.salarydetail.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryOutputDistinction;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSetting;

@Getter
@Setter
public class SaveSalaryPrintSettingCommand {
	/** The company code. */
	private CompanyCode companyCode;

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
	
	public SalaryPrintSetting toDomain(){
		//TODO
		return null;
	}
}
