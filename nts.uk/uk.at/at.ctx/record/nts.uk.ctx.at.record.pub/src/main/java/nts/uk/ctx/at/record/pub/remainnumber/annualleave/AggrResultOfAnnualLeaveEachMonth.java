package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnualLeave;
/**
 * 年月毎年休の集計結果
 */
@Getter
@Setter
public class AggrResultOfAnnualLeaveEachMonth {
	private YearMonth yearMonth;
	private AggrResultOfAnnualLeave aggrResultOfAnnualLeave;
	
	public AggrResultOfAnnualLeaveEachMonth(YearMonth yearMonth, AggrResultOfAnnualLeave aggrResultOfAnnualLeave) {
		super();
		this.yearMonth = yearMonth;
		this.aggrResultOfAnnualLeave = aggrResultOfAnnualLeave;
	}
}
