package nts.uk.screen.at.app.command.kmk.kmk004.i;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DeleteFlexMonthlyWorkTimeSetEmpCommand {

	// 年度
	private int year;
	// 雇用コード 
	private String employmentCode;
}
