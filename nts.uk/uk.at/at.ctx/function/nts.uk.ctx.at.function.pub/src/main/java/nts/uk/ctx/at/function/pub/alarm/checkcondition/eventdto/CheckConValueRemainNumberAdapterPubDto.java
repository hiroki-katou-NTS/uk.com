package nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class CheckConValueRemainNumberAdapterPubDto {
	
	private int daysValue;
	
	private Integer timeValue;

	public CheckConValueRemainNumberAdapterPubDto(int daysValue, Integer timeValue) {
		super();
		this.daysValue = daysValue;
		this.timeValue = timeValue;
	}
	
}
