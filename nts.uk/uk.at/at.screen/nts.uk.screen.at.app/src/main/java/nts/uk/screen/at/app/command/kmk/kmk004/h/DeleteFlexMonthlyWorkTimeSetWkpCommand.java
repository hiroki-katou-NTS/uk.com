package nts.uk.screen.at.app.command.kmk.kmk004.h;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author sonnlb
 *
 *         職場別月単位労働時間（フレックス勤務）を削除する
 */
@AllArgsConstructor
@Data
public class DeleteFlexMonthlyWorkTimeSetWkpCommand {
	// 年度
	private int year;
	// 職場ID
	private String workplaceId;
}
