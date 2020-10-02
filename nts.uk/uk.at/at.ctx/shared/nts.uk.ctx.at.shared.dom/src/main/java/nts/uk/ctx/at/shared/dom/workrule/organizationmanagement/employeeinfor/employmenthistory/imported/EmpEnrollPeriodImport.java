package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported;

import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 社員の在籍期間Imported
 * 
 * @author HieuLt
 *
 */
@Getter
public class EmpEnrollPeriodImport {

	/** 社員ID **/
	private final String empID;

	/** 期間 (Period) **/
	private final DatePeriod datePeriod;

	/** 出向状況 (Enum) **/
	private final SecondSituation secondedSituation;

	// [C-0] 社員の在籍期間Imported( 社員ID, 期間, 出向状況 )
	public EmpEnrollPeriodImport(String empID, DatePeriod datePeriod, SecondSituation secondedSituation) {
		super();
		this.empID = empID;
		this.datePeriod = datePeriod;
		this.secondedSituation = secondedSituation;
	}

}
