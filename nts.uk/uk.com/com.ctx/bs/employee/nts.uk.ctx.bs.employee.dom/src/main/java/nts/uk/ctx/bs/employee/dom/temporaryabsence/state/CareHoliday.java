/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

import lombok.AllArgsConstructor;

/**
 * @author danpv
 *
 */
@AllArgsConstructor
public class CareHoliday extends LeaveHolidayState{
	
	/**
	 * 家族メンバーId Family member id
	 */
	private String familyMemberId;
	

	public String getFamilyMemberId() {
		return familyMemberId;
	}

	public void setFamilyMemberId(String familyMemberId) {
		this.familyMemberId = familyMemberId;
	}

}
