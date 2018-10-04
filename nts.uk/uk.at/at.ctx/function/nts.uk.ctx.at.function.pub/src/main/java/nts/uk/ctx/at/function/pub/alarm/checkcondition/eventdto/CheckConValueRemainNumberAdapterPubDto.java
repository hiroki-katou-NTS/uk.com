package nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class CheckConValueRemainNumberAdapterPubDto {
	
	private BigDecimal daysValue;
	
	private Integer timeValue;

	public CheckConValueRemainNumberAdapterPubDto(BigDecimal daysValue, Integer timeValue) {
		super();
		this.daysValue = daysValue;
		this.timeValue = timeValue;
	}
	
}
