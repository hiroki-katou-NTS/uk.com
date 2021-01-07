package nts.uk.ctx.at.function.dom.processexecution.tasksetting;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.EndTimeClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.EndTime;

/**
 * 実行タスク終了時刻設定
 */
@Getter
@AllArgsConstructor
public class TaskEndTime {
	/* 実行タスク終了時刻ありなし区分 */
	private EndTimeClassification endTimeCls;
	
	/* 終了時刻 */
	private Optional<EndTime> endTime;
}
