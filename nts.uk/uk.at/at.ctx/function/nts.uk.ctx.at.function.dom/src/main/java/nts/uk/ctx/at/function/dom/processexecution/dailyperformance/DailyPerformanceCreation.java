package nts.uk.ctx.at.function.dom.processexecution.dailyperformance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 日別実績の作成・計算
 */
@Getter
@Setter
@AllArgsConstructor
public class DailyPerformanceCreation {
	/* 日別実績の作成・計算 */
	private boolean dailyPerfCls;
	
	/* 作成・計算項目 */
	private DailyPerformanceItem dailyPerfItem;
	
	/* 途中入社は入社日からにする */
	private boolean midJoinEmployee;
}
