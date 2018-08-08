package nts.uk.file.at.infra.schedule.monthly;

import lombok.Data;
import nts.uk.file.at.app.export.dailyschedule.data.WorkplaceReportData;

/**
 * Report data for daily performance (KWR001).
 *
 * @author HoangNDH
 */
@Data
public class MonthlyPerformanceReportData {
	
	/** The header data. */
	public MonthlyPerformanceHeaderData headerData;
	
	/** The lst workplace report data. */
	public WorkplaceReportData workplaceReportData;
	
	/** The daily report data. */
	public MonthlyReportData monthlyReportData;
}
