package nts.uk.screen.at.app.command.kmk.kmk004.l;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 会社別月単位労働時間（変形労働）を削除する
 * @author tutt
 *
 */
@AllArgsConstructor
@Data
public class DeleteTransMonthlyWorkTimeSetComCommand {
	
	// 年度
	private int year;
}
