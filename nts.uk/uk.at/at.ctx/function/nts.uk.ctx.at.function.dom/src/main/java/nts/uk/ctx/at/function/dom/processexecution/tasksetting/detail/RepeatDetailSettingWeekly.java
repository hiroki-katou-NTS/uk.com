package nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 繰り返し詳細設定(毎週)
 */
@Getter
@AllArgsConstructor
public class RepeatDetailSettingWeekly {
	/* 曜日 */
	private RepeatWeekDaysSelect weekdaySetting;
	
	/* 繰り返し間隔（週） */
	private WeeklyWeekSetting weeklyWeekSetting;
}
