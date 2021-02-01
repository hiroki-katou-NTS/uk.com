package nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha;

import lombok.Value;

/**
 * 
 * @author chungnt
 *
 */

@Value
public class DeleteMonthlyWorkTimeSetShaByYearMonthCommand {

	// 社員ID
		private String empId;
		// 勤務区分
		private int laborAttr;
		// 年月
		private int yearMonth;
	
}
