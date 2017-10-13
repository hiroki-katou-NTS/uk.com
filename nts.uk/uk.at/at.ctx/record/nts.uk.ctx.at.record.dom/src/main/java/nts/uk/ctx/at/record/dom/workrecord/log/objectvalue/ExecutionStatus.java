/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.objectvalue;

/**
 * @author danpv
 *
 */
public enum ExecutionStatus {
	
	// 完了
	Done(0),
	
	// 処理中
	Processing(1),
	
	// 未完了
	Incomplete(2);
	
	public final int value;
	
	private ExecutionStatus(int value) {
		this.value = value;
	}

}
