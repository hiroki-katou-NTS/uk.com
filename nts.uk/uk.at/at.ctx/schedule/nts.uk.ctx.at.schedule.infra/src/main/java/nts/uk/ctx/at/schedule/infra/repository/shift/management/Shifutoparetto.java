package nts.uk.ctx.at.schedule.infra.repository.shift.management;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Shifutoparetto {
	
	private int positionNumber;
	private int order;
	private String shiftCode;
	private int page;

}
