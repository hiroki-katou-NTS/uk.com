/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.enums;

/**
 * @author danpv
 *
 */
public enum ExecutionContent {

	// 日別作成
	DailyCreation(0),

	// 日別計算
	DailyCalculation(1),

	// 承認結果反映
	ReflectApprovalResult(2),

	// 月別集計
	MonthlyAggregation(3);

	public final int value;

	private ExecutionContent(int value) {
		this.value = value;
	}

}
