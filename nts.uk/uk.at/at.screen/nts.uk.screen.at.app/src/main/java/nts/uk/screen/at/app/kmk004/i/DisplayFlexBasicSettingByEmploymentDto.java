package nts.uk.screen.at.app.kmk004.i;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.kmk004.g.GetFlexPredWorkTimeDto;

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
	
	// 会社別フレックス勤務集計方法
	GetFlexPredWorkTimeDto flexPredWorkTime;
}
