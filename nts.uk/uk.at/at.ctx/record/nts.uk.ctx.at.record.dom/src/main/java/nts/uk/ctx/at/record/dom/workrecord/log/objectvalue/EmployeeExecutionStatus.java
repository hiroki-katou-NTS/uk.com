/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.objectvalue;

/**
 * @author danpv
 *
 */
public enum EmployeeExecutionStatus {

	Complete(0),

	Incomplete(1);

	public final int value;

	private EmployeeExecutionStatus(int value) {
		this.value = value;
	}
}
