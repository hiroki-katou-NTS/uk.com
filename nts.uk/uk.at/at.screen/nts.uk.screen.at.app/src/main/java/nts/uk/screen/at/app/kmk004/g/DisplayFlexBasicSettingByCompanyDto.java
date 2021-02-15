package nts.uk.screen.at.app.kmk004.g;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnlb
 *
 *         会社別基本設定（フレックス勤務）を表示する
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayFlexBasicSettingByCompanyDto {
	// 会社別フレックス勤務集計方法
	private ComFlexMonthActCalSetDto comFlexMonthActCalSet;
}
