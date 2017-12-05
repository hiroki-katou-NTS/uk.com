/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;

/**
 * @author danpv Domain Name : 産前休業
 *
 */
public class MidweekClosure extends TempAbsenceHisItem {

	/**
	 * Type: Optional 多胎妊娠区分 Multiple pregnancy segment
	 */
	private Boolean multiple;

	public MidweekClosure() {
		super();
	}

	private MidweekClosure(String historyId, String employeeId, GenericString remarks, Integer soInsPayCategory,
			Boolean multiple, String familyMemberId) {
		super(LeaveHolidayType.MIDWEEK_CLOSURE, historyId, employeeId, remarks, soInsPayCategory, familyMemberId);
		this.multiple = multiple;
	}

	public static MidweekClosure init(String historyId, String employeeId, String remarks, Integer soInsPayCategory,
			Boolean multiple, String familyMemberId) {
		return new MidweekClosure(historyId, employeeId, new GenericString(remarks), soInsPayCategory, multiple,
				familyMemberId);
	}

	public Boolean getMultiple() {
		return multiple;
	}

	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

}
