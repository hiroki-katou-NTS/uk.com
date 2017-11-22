/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

/**
 * @author danpv
 *
 */
public class Leave extends LeaveHolidayState {

	/**
	 * 理由 reason
	 */
	private String reason;
	
	/**
	 * Constructor
	 * @param reason
	 */
	public Leave(String reason) {
		super();
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
