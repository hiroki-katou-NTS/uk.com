package nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 繰り返し詳細設定(毎週)
 */
@Getter
@AllArgsConstructor
public class RepeatDetailSettingWeekly extends DomainObject {
	/* 曜日 */
	private RepeatWeekDaysSelect weekdaySetting;
	
}
