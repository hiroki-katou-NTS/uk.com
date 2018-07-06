package nts.uk.file.at.app.export.dailyschedule.data;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.file.at.app.export.dailyschedule.ActualValue;

/**
 * The Class DetailedDailyPerformanceReportData.
 * @author HoangNDH
 */
@Data
public class DetailedDailyPerformanceReportData {
	
	/** The error alarm mark. */
	public String errorAlarmMark;
	
	/** The date. */
	public GeneralDate date;
	
	/** The day of week. */
	public String dayOfWeek;
	
	/** The error detail. */
	public String errorDetail;
	
	/** The actual value. */
	public List<ActualValue> actualValue;
}
