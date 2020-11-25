package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit;

import lombok.Getter;
import nts.arc.time.YearMonth;

/** 職場別月単位労働時間 */
public class MonthlyWorkTimeSetWkp extends MonthlyWorkTimeSet {

	/***/
	private static final long serialVersionUID = 1L;
	
	@Getter
	/** 職場ID */
	private String workplaceId;
	
	private MonthlyWorkTimeSetWkp(String comId, String workplaceId, LaborWorkTypeAttr laborAttr, 
			YearMonth ym, MonthlyLaborTime laborTime) {
		
		super(comId, laborAttr, ym, laborTime);
		
		this.workplaceId = workplaceId;
	}
	
	public static MonthlyWorkTimeSetWkp of (String comId, String workplaceId, 
			LaborWorkTypeAttr laborAttr, YearMonth ym, MonthlyLaborTime laborTime) {
		
		return new MonthlyWorkTimeSetWkp(comId, workplaceId, laborAttr, ym, laborTime);
	}
}
