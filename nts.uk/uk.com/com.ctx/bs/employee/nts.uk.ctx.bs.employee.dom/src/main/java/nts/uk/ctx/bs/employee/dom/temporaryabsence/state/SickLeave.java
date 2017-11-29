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
	private SickLeave(String historyId, String employeeId, GenericString remarks, Integer soInsPayCategory) {
		super(LeaveHolidayType.SICK_LEAVE, historyId, employeeId, remarks, soInsPayCategory);
	}

	public static SickLeave init(String historyId, String employeeId, String remarks,
			Integer soInsPayCategory) {
		return new SickLeave(historyId, employeeId, new GenericString(remarks), soInsPayCategory);
	}

}
