package nts.uk.screen.at.app.command.kmk.kmk004;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnlb
 *
 */
@NoArgsConstructor
@Data
public class MonthlyWorkTimeSetCommand {

	/** 勤務区分 */
	private int laborAttr;

	/** 年月 */
	private int yearMonth;

	/** 月労働時間 */
	private MonthlyLaborTimeCommand laborTime;

	public MonthlyWorkTimeSetCommand(int laborAttr, int yearMonth, MonthlyLaborTimeCommand laborTime) {
		super();
		this.laborAttr = laborAttr;
		this.yearMonth = yearMonth;
		this.laborTime = laborTime;
	}

}
