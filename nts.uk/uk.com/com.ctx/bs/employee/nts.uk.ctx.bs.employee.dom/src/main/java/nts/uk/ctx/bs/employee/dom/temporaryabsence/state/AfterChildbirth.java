/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempLeaveAbsenceHisItem;

/**
 * @author danpv
 * Domain Name : 産後休業
 *
 */
public class AfterChildbirth extends TempLeaveAbsenceHisItem {

	/**
	 * Optional
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
