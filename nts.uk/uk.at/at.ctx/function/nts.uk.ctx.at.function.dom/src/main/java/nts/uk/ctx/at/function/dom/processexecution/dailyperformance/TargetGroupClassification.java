package nts.uk.ctx.at.function.dom.processexecution.dailyperformance;

import lombok.AllArgsConstructor;
//更新処理の日別処理対象者区分
@AllArgsConstructor
public enum TargetGroupClassification {
	//全員を対象に通常実行
	NORMAL_EXECUTION_FOR_ALL(0,"全員を対象に通常実行"),
	//勤務種別変更者のみ再作成
	RECREATE_ONLY_WHO_CHANGED_WORK_TYPE(1,"勤務種別変更者のみ再作成");
	
	public final int value;
	
	public final String name;
}
