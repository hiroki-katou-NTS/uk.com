package nts.uk.ctx.at.function.dom.processexecution.tasksetting;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
//import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatDetailSettingDaily;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatDetailSettingMonthly;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatDetailSettingWeekly;

/**
 * 繰り返し詳細設定
 */
@Getter
@AllArgsConstructor
public class RepeatDetailSetting extends DomainObject {
	
	/* 繰り返し詳細設定(毎週) */
	private Optional<RepeatDetailSettingWeekly> weekly;
	
	/* 繰り返し詳細設定(毎月) */
	private Optional<RepeatDetailSettingMonthly> monthly;
}
