package nts.uk.file.at.app.export.dailyschedule.data;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * Instantiates a new daily report data.
 * @author HoangNDH
 */
@Data
public class WorkplaceDailyReportData {
	
	/** The date. */
	public GeneralDate date;
	
	/** The lst workplace data. */
	public DailyWorkplaceData lstWorkplaceData;
}
