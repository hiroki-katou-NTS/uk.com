package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit;

import lombok.Getter;
import nts.arc.time.YearMonth;

/** 社員別月単位労働時間 */
public class MonthlyWorkTimeSetSha extends MonthlyWorkTimeSet {

	/***/
	private static final long serialVersionUID = 1L;
	
	@Getter
	/** 社員ID */
	private String empId;
	
	private MonthlyWorkTimeSetSha(String comId, String empId, LaborWorkTypeAttr laborAttr, 
			YearMonth ym, MonthlyLaborTime laborTime) {
		
		super(comId, laborAttr, ym, laborTime);
		
		this.empId = empId;
	}
	
	public static MonthlyWorkTimeSetSha of (String comId, String empId, 
			LaborWorkTypeAttr laborAttr, YearMonth ym, MonthlyLaborTime laborTime) {
		
		return new MonthlyWorkTimeSetSha(comId, empId, laborAttr, ym, laborTime);
	}
}
