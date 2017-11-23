/**
 * 6:00:49 PM Aug 28, 2017
 */
package nts.uk.screen.at.ws.dailyperformance.correction;

import java.util.List;

import nts.uk.screen.at.app.dailyperformance.correction.dto.CorrectionOfDailyPerformance;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceEmployeeDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;

/**
 * @author hungnm
 *
 */
public class DPParams {
	public DateRange dateRange;
	public List<DailyPerformanceEmployeeDto> lstEmployee;
	public int displayFormat;
	public CorrectionOfDailyPerformance correctionOfDaily;
	public List<String> errorCodes;
}
