package nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail;

import java.util.List;

import lombok.AllArgsConstructor;

import lombok.Getter;

/**
 * 繰り返し詳細設定(毎月)
 */
@Getter
@AllArgsConstructor
public class RepeatDetailSettingMonthly {
	/* 日 */
	private List<RepeatMonthDaysSelect> days;
	
	/* 月 */
	private RepeatMonthSelect month;
}
