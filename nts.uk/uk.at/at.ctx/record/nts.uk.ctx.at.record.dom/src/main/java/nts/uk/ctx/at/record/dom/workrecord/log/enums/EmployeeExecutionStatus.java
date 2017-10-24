/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.enums;

/**
 * @author danpv
 *
 */
public enum EmployeeExecutionStatus {

	/**
	 * 0 : 完了
	 */
	COMPLETE(0),

	/**
	 * 1 : 未完了
	 */
	INCOMPLETE(1);

	public final int value;

	private EmployeeExecutionStatus(int value) {
		this.value = value;
	}
}
