package nts.uk.file.at.infra.schedule.monthly;

import java.util.List;

import lombok.Data;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalValue;

/**
 * The Class DailyReportData.
 * @author HoangNDH
 */
@Data
public class MonthlyReportData {
	/** The lst daily report data. */
	public List<WorkplaceMonthlyReportData> lstMonthlyReportData;
	
	/** The list total value. */
	public List<TotalValue> listTotalValue;
}
