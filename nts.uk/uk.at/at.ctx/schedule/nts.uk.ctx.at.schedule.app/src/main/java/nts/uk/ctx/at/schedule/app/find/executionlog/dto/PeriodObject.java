package nts.uk.ctx.at.schedule.app.find.executionlog.dto;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * The Class PeriodObject.
 */
@Getter
public class PeriodObject {
	
	/** The start date. */
	public GeneralDate startDate;
	
	/** The end date. */
	public GeneralDate endDate;
}
