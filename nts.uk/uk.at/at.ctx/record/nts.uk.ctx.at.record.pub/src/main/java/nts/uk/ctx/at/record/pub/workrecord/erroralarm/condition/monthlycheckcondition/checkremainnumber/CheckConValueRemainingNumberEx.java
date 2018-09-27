package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class CheckConValueRemainingNumberEx {
	
	private BigDecimal daysValue;
	
	private Integer timeValue;

	public CheckConValueRemainingNumberEx(BigDecimal daysValue, Integer timeValue) {
		super();
		this.daysValue = daysValue;
		this.timeValue = timeValue;
	}
	
}
