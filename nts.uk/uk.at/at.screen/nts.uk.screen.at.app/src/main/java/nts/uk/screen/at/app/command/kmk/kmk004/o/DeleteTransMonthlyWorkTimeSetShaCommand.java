package nts.uk.screen.at.app.command.kmk.kmk004.o;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社員別月単位労働時間（変形労働）を削除する
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DeleteTransMonthlyWorkTimeSetShaCommand {

	// 年度
	private int year;

	// 社員ID
	private String employeeId;
}
