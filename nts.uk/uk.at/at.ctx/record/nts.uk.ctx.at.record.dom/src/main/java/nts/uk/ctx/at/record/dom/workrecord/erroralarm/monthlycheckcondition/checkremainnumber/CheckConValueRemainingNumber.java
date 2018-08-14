package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

@Getter
public class CheckConValueRemainingNumber extends DomainObject {

	private BigDecimal daysValue;
	
	private Optional<Integer> timeValue;

	public CheckConValueRemainingNumber(BigDecimal daysValue, Optional<Integer> timeValue) {
		super();
		this.daysValue = daysValue;
		this.timeValue = timeValue;
	}
	
	
	
}
