package nts.uk.ctx.at.request.pub.vacation.history.export;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * Instantiates a new history export.
 *
 * @param historyId the history id
 * @param startDate the start date
 * @param endDate the end date
 */
@Getter
@Setter
public class HistoryExport {
	
	/** The history id. */
	private String historyId;
	
	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;

}
