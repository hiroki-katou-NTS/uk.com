package nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author sonnlb
 * 
 *         職場別月単位労働時間を複写する
 */
@Value
public class CopyMonthlyWorkTimeSetCommand {
	// 複写元職場ID
	private String copySource;
	// 勤務区分
	private int laborAttr;
	// 年度
	private int year;
	// 複写先職場ID（List）
	private List<String> copyDestinations;
}
