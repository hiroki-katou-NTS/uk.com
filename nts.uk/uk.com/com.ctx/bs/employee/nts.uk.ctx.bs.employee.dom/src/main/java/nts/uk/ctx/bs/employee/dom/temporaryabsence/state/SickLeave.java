/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;

/**
 * @author danpv Domain Name : 傷病休業
 */
public class SickLeave extends TempAbsenceHisItem {

	/**
	 * @param historyId
	 * @param employeeId
	 * @param remarks
	 * @param soInsPayCategory
	 */
	private SickLeave(String historyId, String employeeId, GenericString remarks, Integer soInsPayCategory,
			String familyMemberId) {
		super(LeaveHolidayType.SICK_LEAVE, historyId, employeeId, remarks, soInsPayCategory, familyMemberId);
	}

	public static SickLeave init(String historyId, String employeeId, String remarks, Integer soInsPayCategory,
			String familyMemberId) {
		return new SickLeave(historyId, employeeId, new GenericString(remarks), soInsPayCategory, familyMemberId);
	}

}
