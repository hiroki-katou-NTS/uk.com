package nts.uk.file.at.app.export.dailyschedule.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalCountDay;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalValue;

/**
 * Instantiates a new employee report data.
 * @author HoangNDH
 */
@Data
public class EmployeeReportData {
	
	/** The employee id. */
	public String employeeId;
	
	/** The employee name. */
	public String employeeName;
	
	/** The employment name. */
	public String employmentName;
	
	/** The position. */
	public String position;
	
	/** The lst detailed performance. */
	public List<DetailedDailyPerformanceReportData> lstDetailedPerformance;
	
	/** The total count day. */
	public TotalCountDay totalCountDay = new TotalCountDay();
	
	/** The personal total. */
	public Map<Integer, String> mapPersonalTotal = new HashMap<>();
}
