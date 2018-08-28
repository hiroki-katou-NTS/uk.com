/**
 * 10:59:58 AM Aug 22, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hungnm - 年休残数
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearHolidaySettingDto {

	private boolean manageYearOff;

	private boolean manageTimeOff;

	private Integer annualLeaveRemain;
	
	private Integer timeRemain;
	
}
