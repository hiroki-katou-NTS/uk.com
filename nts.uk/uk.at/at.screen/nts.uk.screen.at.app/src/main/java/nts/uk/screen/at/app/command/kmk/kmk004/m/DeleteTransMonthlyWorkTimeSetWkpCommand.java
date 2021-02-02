package nts.uk.screen.at.app.command.kmk.kmk004.m;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 職場別月単位労働時間（変形労働）を削除する
 * @author tutt
 *
 */
@AllArgsConstructor
@Data
public class DeleteTransMonthlyWorkTimeSetWkpCommand {
	
	// 年度
	private int year;
	
	// 職場ID
	private String workplaceId;
}
