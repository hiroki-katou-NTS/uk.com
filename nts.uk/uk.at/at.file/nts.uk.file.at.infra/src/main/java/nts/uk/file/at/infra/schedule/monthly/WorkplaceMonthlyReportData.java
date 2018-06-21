package nts.uk.file.at.infra.schedule.monthly;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * Instantiates a new daily report data.
 * @author HoangNDH
 */
@Data
public class WorkplaceMonthlyReportData {
	
	/** The date. */
	public GeneralDate date;
	
	/** The lst workplace data. */
	public MonthlyWorkplaceData lstWorkplaceData;
}
