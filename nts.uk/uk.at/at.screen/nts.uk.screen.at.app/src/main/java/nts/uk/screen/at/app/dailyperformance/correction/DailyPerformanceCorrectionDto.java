/**
 * 1:59:07 PM Aug 21, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hungnm
 *
 */
@Getter
@Setter
public class DailyPerformanceCorrectionDto {
	
	private YearHolidaySettingDto yearHolidaySettingDto;
	
	private SubstVacationDto substVacationDto;
	
	private CompensLeaveComDto compensLeaveComDto;
	
	private Com60HVacationDto com60HVacationDto;
	
	private DateRange dateRange;
	
	private List<DailyPerformanceEmployeeDto> lstEmployee;
	
	private DPControlDisplayItem lstControlDisplayItem;
	
	private List<DPCellStateDto> lstCellState;
	
	private List<DPDataDto> lstData;

	public DailyPerformanceCorrectionDto() {
		super();
	}
	
}
