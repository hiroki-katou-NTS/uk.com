package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;

@AllArgsConstructor
@NoArgsConstructor
public class ActualStatusCheckResult {
	
	/**
	 * 実績状態
	 */
	public ActualStatus actualStatus;
	
	/**
	 * 実績
	 */
	public String workType; // 出勤時刻
	public String workTime; // 退勤時刻
	public Integer startTime; // 勤務種類
	public Integer endTime; // 就業時間帯
	/**
	 * 申請時間
	 */
	public ApplicationTime applicationTime;
}
