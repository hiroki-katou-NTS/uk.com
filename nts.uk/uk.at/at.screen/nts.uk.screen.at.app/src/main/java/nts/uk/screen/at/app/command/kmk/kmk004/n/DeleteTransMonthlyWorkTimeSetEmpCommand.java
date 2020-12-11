package nts.uk.screen.at.app.command.kmk.kmk004.n;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 雇用別月単位労働時間（変形労働）を削除する
 * @author tutt
 *
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DeleteTransMonthlyWorkTimeSetEmpCommand {

	// 年度
	private int year;
	
	// 雇用コード 
	private String employmentCode;
}
