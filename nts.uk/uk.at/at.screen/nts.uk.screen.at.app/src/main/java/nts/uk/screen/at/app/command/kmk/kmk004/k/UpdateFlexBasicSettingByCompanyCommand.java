package nts.uk.screen.at.app.command.kmk.kmk004.k;

import lombok.Value;

/**
 * 
 * @author sonnlb
 *
 */
@Value
public class UpdateFlexBasicSettingByCompanyCommand {
	// フレックス勤務所定労働時間取得
	private GetFlexPredWorkTimeCommand flexPredWorkTime;
	// 会社別フレックス勤務集計方法
	private ComFlexMonthActCalSetCommand flexMonthActCalSet;

}
