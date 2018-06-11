package nts.uk.file.at.app.export.dailyschedule.data;

import lombok.Data;

/**
 * Report data for daily performance (KWR001).
 *
 * @author HoangNDH
 */
@Data
public class DailyPerformanceReportData {
	
	/** The header data. */
	public DailyPerformanceHeaderData headerData;
	
	/** The lst workplace report data. */
	public WorkplaceReportData workplaceReportData;
	
	/** The daily report data. */
	public DailyReportData dailyReportData;
}
