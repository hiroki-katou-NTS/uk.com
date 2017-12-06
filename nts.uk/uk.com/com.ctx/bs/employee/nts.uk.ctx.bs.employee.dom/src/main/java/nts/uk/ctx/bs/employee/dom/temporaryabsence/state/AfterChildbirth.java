/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;

/**
 * @author danpv Domain Name : 産後休業
 *
 */
public class AfterChildbirth extends TempAbsenceHisItem {

	/**
	 * Optional 家族メンバーId Family member id
	 */
	private String familyMemberId;

	public AfterChildbirth() {
		super();
	}

	private AfterChildbirth(String historyId, String employeeId, GenericString remarks, Integer soInsPayCategory,
			String familyMemberId) {
		super(LeaveHolidayType.AFTER_CHILDBIRTH, historyId, employeeId, remarks, soInsPayCategory);
		this.familyMemberId = familyMemberId;
	}

	public static AfterChildbirth init(String historyId, String employeeId, String remarks,
			Integer soInsPayCategory, String familyMemberId) {
		return new AfterChildbirth(historyId, employeeId, new GenericString(remarks), soInsPayCategory, familyMemberId);
	}

	public String getFamilyMemberId() {
		return familyMemberId;
	}

	public void setFamilyMemberId(String familyMemberId) {
		this.familyMemberId = familyMemberId;
	}

}
