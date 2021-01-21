package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

@AllArgsConstructor
@NoArgsConstructor
public class DeductionTimeDto {
	/** The start. */
	public Integer start;

	/** The end. */
	public Integer end;
	
	
	public static DeductionTimeDto fromDomain(DeductionTime deductionTime) {
		
		return new DeductionTimeDto(
				deductionTime.getStart().v(),
				deductionTime.getEnd().v());
	}
}
