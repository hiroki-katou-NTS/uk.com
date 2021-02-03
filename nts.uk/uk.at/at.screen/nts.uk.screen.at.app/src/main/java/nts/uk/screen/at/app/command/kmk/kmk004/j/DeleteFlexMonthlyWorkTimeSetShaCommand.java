package nts.uk.screen.at.app.command.kmk.kmk004.j;

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
public class DeleteFlexMonthlyWorkTimeSetShaCommand {

	// 年度
	private int year;
	// 社員ID
	private String sId;
}
