package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BreakTime {
	/**
	 * 開始時刻
	 */
	private Integer startTime;
	/**
	 * 終了時刻
	 */
	private Integer endTime;
}
