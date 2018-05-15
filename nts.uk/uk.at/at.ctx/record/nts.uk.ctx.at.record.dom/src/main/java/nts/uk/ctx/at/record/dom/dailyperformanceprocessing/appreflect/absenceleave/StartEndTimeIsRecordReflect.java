package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.absenceleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StartEndTimeIsRecordReflect {
	/**反映できるフラグ	 */
	private boolean chkReflect;
	/**反映開始時刻	 */
	private Integer startTime;
	/**反映終了時刻	 */
	private Integer endTime;
}
