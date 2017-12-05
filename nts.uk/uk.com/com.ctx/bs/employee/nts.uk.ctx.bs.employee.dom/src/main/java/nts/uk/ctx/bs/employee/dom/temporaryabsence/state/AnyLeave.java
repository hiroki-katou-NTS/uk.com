/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;

/**
 * @author danpv Domain Name : 任意休業
 */
public class AnyLeave extends TempAbsenceHisItem {

	/**
	 * @param historyId
	 * @param employeeId
	 * @param remarks
	 * @param soInsPayCategory
	 */
	private AnyLeave(String historyId, String employeeId, GenericString remarks, Integer soInsPayCategory,
			String familyMemberId) {
		super(LeaveHolidayType.ANY_LEAVE, historyId, employeeId, remarks, soInsPayCategory, familyMemberId);
	}

	public static AnyLeave init(String historyId, String employeeId, String remarks, Integer soInsPayCategory,
			String familyMemberId) {
		return new AnyLeave(historyId, employeeId, new GenericString(remarks), soInsPayCategory, familyMemberId);
	}

}
