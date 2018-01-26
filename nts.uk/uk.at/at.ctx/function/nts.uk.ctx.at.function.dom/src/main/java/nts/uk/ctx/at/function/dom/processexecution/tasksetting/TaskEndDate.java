package nts.uk.ctx.at.function.dom.processexecution.tasksetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndDateClassification;

/**
 * 実行タスク終了日
 */
@Getter
@AllArgsConstructor
public class TaskEndDate {
	/* 実行タスク終了日区分 */
	private EndDateClassification endDateCls;
	
	/* 終了日日付指定 */
	private GeneralDate endDate;
}
