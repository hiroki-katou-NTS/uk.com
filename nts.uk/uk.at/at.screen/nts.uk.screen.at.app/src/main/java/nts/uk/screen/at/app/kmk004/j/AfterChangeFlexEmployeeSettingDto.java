package nts.uk.screen.at.app.kmk004.j;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnlb
 *
 */
@NoArgsConstructor
@Data
public class AfterChangeFlexEmployeeSettingDto {

	// 社員別フレックス勤務集計方法

	private ShaFlexMonthActCalSetDto flexMonthActCalSet;

	private List<String> alreadySettings;
}
