package nts.uk.file.at.app.export.dailyschedule.data;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

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
