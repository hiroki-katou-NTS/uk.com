/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.objectvalue;

/**
 * @author danpv
 *
 */
public enum ExecutionType {
	
	// 通常実行
	NormalExecution(0),
	
	// 再実行
	Rerun(1);
	
	public final int value;
	
	private ExecutionType(int value) {
		this.value = value;
	}

}
