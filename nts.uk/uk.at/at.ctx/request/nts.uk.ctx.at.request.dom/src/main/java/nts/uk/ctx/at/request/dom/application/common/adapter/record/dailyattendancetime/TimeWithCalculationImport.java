package nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeWithCalculationImport {
	/**
	 * time 時間
	 */
	private Integer time;
	/**
	 * calTime 計算時間
	 */
	private Integer calTime;
}
