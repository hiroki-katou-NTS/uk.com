/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.enums;

/**
 * @author danpv
 *
 */
public enum ExeStateOfCalAndSum {

	//0: 完了
	DONE(0),
	
	//1: 完了（エラーあり）
	DONE_WITH_ERROR(1),

	//2: 処理中
	PROCESSING(2),

	//3:実行中止
	STOPPING(3),

	//4: 中断開始
	START_INTERRUPTION(4),

	//5: 中断終了
	END_INTERRUPTION(5);

	public final int value;

	private ExeStateOfCalAndSum(int value) {
		this.value = value;
	}

}
