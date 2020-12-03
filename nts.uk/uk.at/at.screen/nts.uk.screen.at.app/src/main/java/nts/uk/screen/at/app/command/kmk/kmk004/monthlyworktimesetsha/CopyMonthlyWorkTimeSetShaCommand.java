package nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetsha;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author sonnlb
 * 
 *         社員別月単位労働時間を複写する
 */
@Value
public class CopyMonthlyWorkTimeSetShaCommand {
	// 複写元社員ID
	private String copySourceSid;
	// 勤務区分
	private int laborAttr;
	// 年度
	private int year;
	// 複写先社員ID（List）
	private List<String> copyDestinationSids;
}
