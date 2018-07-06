package nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompareRangeAdapterPubDto {
	private int compareOperator;
	
	private CheckConValueRemainNumberAdapterPubDto startValue;
	
	private CheckConValueRemainNumberAdapterPubDto endValue;

	public CompareRangeAdapterPubDto(int compareOperator, CheckConValueRemainNumberAdapterPubDto startValue, CheckConValueRemainNumberAdapterPubDto endValue) {
		super();
		this.compareOperator = compareOperator;
		this.startValue = startValue;
		this.endValue = endValue;
	}
	
	
	
}
