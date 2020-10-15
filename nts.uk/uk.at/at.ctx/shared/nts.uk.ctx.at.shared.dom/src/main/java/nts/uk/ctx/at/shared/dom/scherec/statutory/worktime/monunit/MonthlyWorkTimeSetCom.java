package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit;

import nts.arc.time.YearMonth;

/** 会社別月単位労働時間 */
public class MonthlyWorkTimeSetCom extends MonthlyWorkTimeSet {

	/***/
	private static final long serialVersionUID = 1L;
	
	private MonthlyWorkTimeSetCom(String comId, LaborWorkTypeAttr laborAttr, 
			YearMonth ym, MonthlyLaborTime laborTime) {
		
		super(comId, laborAttr, ym, laborTime);
	}
	
	public static MonthlyWorkTimeSetCom of (String comId, LaborWorkTypeAttr laborAttr, 
			YearMonth ym, MonthlyLaborTime laborTime) {
		
		return new MonthlyWorkTimeSetCom(comId, laborAttr, ym, laborTime);
	}
}
