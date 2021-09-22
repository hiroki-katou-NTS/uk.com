package nts.uk.screen.at.app.kmk004.j;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.kmk004.g.ComFlexMonthActCalSetDto;

/**
 * 
 * @author sonnlb
 *
 */
@NoArgsConstructor
@Data
public class DisplayFlexBasicSettingByEmployeeDto {

	private ShaFlexMonthActCalSetDto flexMonthActCalSet;

//	private GetFlexPredWorkTimeDto flexPredWorkTime;
	
	private ComFlexMonthActCalSetDto comFlexMonthActCalSet;
}
