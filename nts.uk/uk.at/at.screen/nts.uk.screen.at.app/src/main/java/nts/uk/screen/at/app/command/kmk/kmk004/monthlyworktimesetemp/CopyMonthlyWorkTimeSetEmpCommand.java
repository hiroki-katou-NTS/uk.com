package nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetemp;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author sonnlb
 * 
 *         雇用別月単位労働時間を複写する
 */
@Value
public class CopyMonthlyWorkTimeSetEmpCommand {
	// 複写元雇用コード
	private String copySourceEmploymentCode;
	// 勤務区分
	private int laborAttr;
	// 年度
	private int year;
	// 複写先雇用コード（List）
	private List<String> copyDestinationEmploymentCodes;
}
