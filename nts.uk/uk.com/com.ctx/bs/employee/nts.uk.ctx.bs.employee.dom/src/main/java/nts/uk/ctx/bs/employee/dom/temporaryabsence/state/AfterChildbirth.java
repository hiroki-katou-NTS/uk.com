/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

/**
 * @author danpv
 *
 */
public class AfterChildbirth extends LeaveHolidayState {

	/**
	 * 家族メンバーId Family member id
	 */
	private String familyMemberId;
	
	
	
	/**
	 * @param familyMemberId
	 */
	public AfterChildbirth(String familyMemberId) {
		super();
		this.familyMemberId = familyMemberId;
	}

	public String getFamilyMemberId() {
		return familyMemberId;
	}

	public void setFamilyMemberId(String familyMemberId) {
		this.familyMemberId = familyMemberId;
	}

}
