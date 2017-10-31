/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.enums;

/**
 * @author danpv
 *
 */
public enum ExecutionType {
	
	//0: 通常実行
	NORMAL_EXECUTION(0),
	
	//1: 再実行
	RERUN(1);
	
	public final int value;
	
	private ExecutionType(int value) {
		this.value = value;
	}

}
