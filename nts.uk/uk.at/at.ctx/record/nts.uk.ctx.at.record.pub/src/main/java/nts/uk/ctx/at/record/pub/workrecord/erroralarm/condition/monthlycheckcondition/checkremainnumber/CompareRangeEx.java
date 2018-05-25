package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompareRangeEx {
	private int compareOperator;
	
	private CheckConValueRemainingNumberEx startValue;
	
	private CheckConValueRemainingNumberEx endValue;

	public CompareRangeEx(int compareOperator, CheckConValueRemainingNumberEx startValue, CheckConValueRemainingNumberEx endValue) {
		super();
		this.compareOperator = compareOperator;
		this.startValue = startValue;
		this.endValue = endValue;
	}
	
	
	
}
