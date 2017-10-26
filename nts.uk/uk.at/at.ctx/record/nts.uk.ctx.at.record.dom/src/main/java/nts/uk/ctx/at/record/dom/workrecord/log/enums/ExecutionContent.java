/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.enums;

/**
 * @author danpv
 *
 */
public enum ExecutionContent {

	//0: 日別作成
	DAILY_CREATION(0),

	//1: 日別計算
	DAILY_CALCULATION(1),

	//2: 承認結果反映
	REFLRCT_APPROVAL_RESULT(2),

	//3: 月別集計
	MONTHLY_AGGREGATION(3);

	public final int value;

	private ExecutionContent(int value) {
		this.value = value;
	}

}
