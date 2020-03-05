package nts.uk.ctx.at.schedule.infra.repository.shift.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@AllArgsConstructor
@Data
public class Shifutoparetto {
	
	public int positionNumber;
	public int order;
	public String shiftCode;
	public int page;

}
