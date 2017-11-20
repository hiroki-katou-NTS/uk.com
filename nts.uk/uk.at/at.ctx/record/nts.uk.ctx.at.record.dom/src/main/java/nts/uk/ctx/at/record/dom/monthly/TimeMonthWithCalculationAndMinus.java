package nts.uk.ctx.at.record.dom.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;

/**
 * 計算付き月間時間（マイナス有り）
 * @author shuichi_ishida
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeMonthWithCalculationAndMinus {

	/** 時間 */
	private AttendanceTimeMonthWithMinus time;
	/** 計算期間 */
	private AttendanceTimeMonthWithMinus calculationTime;
}
