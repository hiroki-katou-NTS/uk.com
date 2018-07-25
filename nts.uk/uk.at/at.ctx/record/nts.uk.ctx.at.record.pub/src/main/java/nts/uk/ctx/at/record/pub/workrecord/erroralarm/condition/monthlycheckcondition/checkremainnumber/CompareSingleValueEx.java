package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompareSingleValueEx {
	private int compareOpertor;
	
	private CheckConValueRemainingNumberEx value;

	public CompareSingleValueEx(int compareOpertor, CheckConValueRemainingNumberEx value) {
		super();
		this.compareOpertor = compareOpertor;
		this.value = value;
	}
	
	
}
