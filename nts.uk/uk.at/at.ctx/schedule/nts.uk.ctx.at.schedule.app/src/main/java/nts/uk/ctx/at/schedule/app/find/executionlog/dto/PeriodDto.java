package nts.uk.ctx.at.schedule.app.find.executionlog.dto;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class PeriodDto.
 */
@Getter
@Setter
public class PeriodDto {
	
	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;
	

	/**
	 * Instantiates a new period dto.
	 *
	 * @param start the start
	 * @param end the end
	 */
	public PeriodDto(GeneralDate start, GeneralDate end) {
		this.startDate = start;
		this.endDate = end;
	}
}
