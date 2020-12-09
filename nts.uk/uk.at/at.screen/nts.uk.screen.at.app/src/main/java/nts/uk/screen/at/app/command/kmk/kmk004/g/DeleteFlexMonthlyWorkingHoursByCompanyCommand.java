package nts.uk.screen.at.app.command.kmk.kmk004.g;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author sonnlb
 *
 *         会社別月単位労働時間（フレックス勤務）を削除する
 */
@AllArgsConstructor
@Data
public class DeleteFlexMonthlyWorkingHoursByCompanyCommand {
	// 年度
	private int year;
}
