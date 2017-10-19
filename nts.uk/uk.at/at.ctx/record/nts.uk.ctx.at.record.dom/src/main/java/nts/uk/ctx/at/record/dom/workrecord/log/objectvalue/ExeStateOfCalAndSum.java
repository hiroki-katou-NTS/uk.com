/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.objectvalue;

/**
 * @author danpv
 *
 */
public enum ExeStateOfCalAndSum {

	// 完了
	Done(0),
	
	// 完了（エラーあり）
	DoneWithError(1),

	// 処理中
	Processing(2),

	//実行中止
	Stopping(3),

	// 中断開始
	StartInterruption(4),

	// 中断終了
	EndInterruption(5);

	public final int value;

	private ExeStateOfCalAndSum(int value) {
		this.value = value;
	}

}
