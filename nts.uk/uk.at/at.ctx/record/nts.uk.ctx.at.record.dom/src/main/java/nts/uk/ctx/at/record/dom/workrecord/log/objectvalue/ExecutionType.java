/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.objectvalue;

/**
 * @author danpv
 *
 */
public enum ExecutionType {
	
	normalExecution(0),
	
	rerun(1);
	
	public final int value;
	
	private ExecutionType(int value) {
		this.value = value;
	}

}
