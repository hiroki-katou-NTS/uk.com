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
	// 会社別フレックス勤務集計方法
	private ComFlexMonthActCalSetCommand flexMonthActCalSet;

}
