package nts.uk.screen.at.app.command.kmk.kmk004;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnlb
 *
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class MonthlyWorkTimeSetCommand {

	/** 勤務区分 */
	private int laborAttr;

	/** 年月 */
	private int ym;

	/** 月労働時間 */
	private MonthlyLaborTimeCommand laborTime;
}
