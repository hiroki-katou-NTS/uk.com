package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

@Getter
public class CheckConValueRemainingNumber extends DomainObject {

	private int daysValue;
	
	private Optional<Integer> timeValue;

	public CheckConValueRemainingNumber(int daysValue, Optional<Integer> timeValue) {
		super();
		this.daysValue = daysValue;
		this.timeValue = timeValue;
	}
	
	
	
}
