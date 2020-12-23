package nts.uk.screen.at.app.command.kmk.kmk004.k;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnlb
 *
 */
@Data
@NoArgsConstructor
public class UpdateFlexBasicSettingByCompanyCommand {
	// フレックス勤務所定労働時間取得
	private GetFlexPredWorkTimeCommand flexPredWorkTime;
	// 会社別フレックス勤務集計方法
	private ComFlexMonthActCalSetCommand flexMonthActCalSet;

}
