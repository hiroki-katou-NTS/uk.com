package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class WorkTimeTypeOutput {
	/**
	 * 予定勤務種類コード
	 */
	private String worktimeCode;
	/**
	 * 予定就業時間帯コード
	 */
	private String workTypeCode;
}
