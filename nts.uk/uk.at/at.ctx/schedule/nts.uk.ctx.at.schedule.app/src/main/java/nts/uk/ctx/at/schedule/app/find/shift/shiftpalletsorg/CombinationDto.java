package nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.Combinations;

@Data

@NoArgsConstructor

public class CombinationDto {
	public int pairNo;
	public String workTypeCode;
	public String workTimeCode;
	public CombinationDto(Combinations c) {
		this.pairNo = c.getOrder();
		this.workTypeCode = c.getShiftCode().v();
		this.workTimeCode = null;
	}
}
