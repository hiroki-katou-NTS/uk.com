package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit;

import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/** 雇用別月単位労働時間 */
public class MonthlyWorkTimeSetEmp extends MonthlyWorkTimeSet {

	/***/
	private static final long serialVersionUID = 1L;
	
	@Getter
	/** 社員ID */
	private EmploymentCode employment;
	
	private MonthlyWorkTimeSetEmp(String comId, EmploymentCode employment, LaborWorkTypeAttr laborAttr, 
			YearMonth ym, MonthlyLaborTime laborTime) {
		
		super(comId, laborAttr, ym, laborTime);
		
		this.employment = employment;
	}
	
	public static MonthlyWorkTimeSetEmp of (String comId, EmploymentCode employment, 
			LaborWorkTypeAttr laborAttr, YearMonth ym, MonthlyLaborTime laborTime) {
		
		return new MonthlyWorkTimeSetEmp(comId, employment, laborAttr, ym, laborTime);
	}
}
