package nts.uk.ctx.at.function.dom.processexecution.dailyperformance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * 日別実績の作成・計算
 */
@Getter
@Setter
@AllArgsConstructor
public class DailyPerformanceCreation extends DomainObject {
	/* 日別実績の作成・計算区分 */
	private boolean dailyPerfCls;

	/* 作成・計算項目 */
	private DailyPerformanceItem dailyPerfItem;

	
	/* 対象者区分 */
	private TargetGroupClassification targetGroupClassification;
}
