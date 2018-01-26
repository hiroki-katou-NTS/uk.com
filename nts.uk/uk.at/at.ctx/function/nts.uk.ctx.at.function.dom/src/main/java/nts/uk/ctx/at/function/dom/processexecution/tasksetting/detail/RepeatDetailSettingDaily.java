package nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 繰り返し詳細設定(毎日)
 */
@Getter
@AllArgsConstructor
public class RepeatDetailSettingDaily {
	/* 繰り返し間隔(日) */
	private DailyDaySetting day;
}
