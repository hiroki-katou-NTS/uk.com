package nts.uk.file.at.app.export.monthlyschedule;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.file.at.app.export.dailyschedule.ActualValue;

/**
 * The Class DetailedMonthlyPerformanceReportData.
 * @author HoangNDH
 */
@Data
public class DetailedMonthlyPerformanceReportData {
	
	/** The error alarm mark. */
	public String errorAlarmMark;
	
	/** The month. */
	public YearMonth yearMonth;
	
	/** The error detail. */
	public String errorDetail;
	
	/** Closure date */
	public String closureDate;
	
	/** The actual value. */
	public List<ActualValue> actualValue;
}
