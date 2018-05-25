package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class CheckConValueRemainingNumberEx {
	
	private int daysValue;
	
	private Optional<Integer> timeValue;

	public CheckConValueRemainingNumberEx(int daysValue, Integer timeValue) {
		super();
		this.daysValue = daysValue;
		this.timeValue = Optional.ofNullable(timeValue);
	}
	
}
