package nts.uk.ctx.at.record.pub.dailyperform.appreflect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BreakTimePubPara {
	/**
	 * 開始時刻
	 */
	private Integer startTime;
	/**
	 * 終了時刻
	 */
	private Integer endTime;
}
