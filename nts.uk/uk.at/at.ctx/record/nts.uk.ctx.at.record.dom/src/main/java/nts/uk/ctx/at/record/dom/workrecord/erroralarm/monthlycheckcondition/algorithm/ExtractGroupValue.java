package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ExtractGroupValue {
	/**
	 * アラーム内容
	 */
	private String alarmDescription;
	/**
	 * チェック対象値
	 */
	private String checkedValue;
}
