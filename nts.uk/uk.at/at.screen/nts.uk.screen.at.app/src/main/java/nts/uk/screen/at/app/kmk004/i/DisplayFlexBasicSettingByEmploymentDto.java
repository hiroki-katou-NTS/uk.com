package nts.uk.screen.at.app.kmk004.i;

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
public class DisplayFlexBasicSettingByEmploymentDto {

	// 雇用を選択する
	EmpFlexMonthActCalSetDto flexMonthActCalSet;
	
	ComFlexMonthActCalSetDto comFlexMonthActCalSet;
}
