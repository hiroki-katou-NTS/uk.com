package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmExtracResult;

@AllArgsConstructor
@Getter
@Setter
public class AlarmListResultDto {
	/**List<アラーム抽出結果>	 */
	private List<AlarmExtracResult> lstAlarmResult;
	/**
	 * 終了状態 True:正常終了、False：中断
	 */
	private boolean extracting;
}
