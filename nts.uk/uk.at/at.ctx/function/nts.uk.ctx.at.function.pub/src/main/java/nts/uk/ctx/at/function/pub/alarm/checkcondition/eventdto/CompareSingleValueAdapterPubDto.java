package nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompareSingleValueAdapterPubDto {
	private int compareOpertor;
	
	private CheckConValueRemainNumberAdapterPubDto value;

	public CompareSingleValueAdapterPubDto(int compareOpertor, CheckConValueRemainNumberAdapterPubDto value) {
		super();
		this.compareOpertor = compareOpertor;
		this.value = value;
	}
	
	
}
