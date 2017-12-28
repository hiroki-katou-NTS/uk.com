package nts.uk.ctx.at.record.dom.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 計算付き月間時間
 * @author shuichi_ishida
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeMonthWithCalculation {

	/** 時間 */
	private AttendanceTimeMonth time;
	/** 計算期間 */
	private AttendanceTimeMonth calculationTime;
}
