package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ScheStartEndTimeReflectOutput {
	/**
	 * 開始時刻
	 */
	private Integer startTime1;
	/**
	 * 終了時刻
	 */
	private Integer endTime1;
	/**
	 * １回勤務反映区分(true, false)
	 */
	private boolean countReflect1Atr;
	/**
	 * 開始時刻2
	 */
	private Integer startTime2;
	/**
	 * 終了時刻2
	 */
	private Integer endTime2;
	/**
	 * ２回勤務反映区分(true, false)
	 */
	private boolean countReflect2Atr;
}
