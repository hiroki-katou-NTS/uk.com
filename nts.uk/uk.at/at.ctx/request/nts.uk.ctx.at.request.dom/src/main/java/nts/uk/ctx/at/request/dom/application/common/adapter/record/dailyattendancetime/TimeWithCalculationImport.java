package nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeWithCalculationImport {
	/**
	 * time
	 */
	private Integer time;
	/**
	 * calTime
	 */
	private Integer calTime;
}
