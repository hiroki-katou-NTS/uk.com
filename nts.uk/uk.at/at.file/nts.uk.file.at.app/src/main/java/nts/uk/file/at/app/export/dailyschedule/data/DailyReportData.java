package nts.uk.file.at.app.export.dailyschedule.data;

import java.util.List;

import lombok.Data;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalValue;

/**
 * The Class DailyReportData.
 * @author HoangNDH
 */
@Data
public class DailyReportData {
	/** The lst daily report data. */
	public List<WorkplaceDailyReportData> lstDailyReportData;
	
	/** The list total value. */
	public List<TotalValue> listTotalValue;
}
