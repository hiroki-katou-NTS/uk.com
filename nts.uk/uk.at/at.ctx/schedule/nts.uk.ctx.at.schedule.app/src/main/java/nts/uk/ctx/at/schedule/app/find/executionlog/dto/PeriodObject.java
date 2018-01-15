package nts.uk.ctx.at.schedule.app.find.executionlog.dto;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;

/**
 * The Class PeriodObject.
 */
@Getter
public class PeriodObject {
	
	/** The start date. */
	public GeneralDateTime startDate;
	
	/** The end date. */
	public GeneralDateTime endDate;
}
