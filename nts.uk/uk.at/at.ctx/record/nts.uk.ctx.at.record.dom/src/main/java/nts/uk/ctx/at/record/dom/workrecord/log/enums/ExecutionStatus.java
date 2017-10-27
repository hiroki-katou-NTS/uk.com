/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.enums;

/**
 * @author danpv
 *
 */
public enum ExecutionStatus {
	
	//0: 完了
	DONE(0),
	
	//1: 処理中
	PROCESSING(1),
	
	//2: 未完了
	INCOMPLETE(2);
	
	public final int value;
	
	private ExecutionStatus(int value) {
		this.value = value;
	}

}
