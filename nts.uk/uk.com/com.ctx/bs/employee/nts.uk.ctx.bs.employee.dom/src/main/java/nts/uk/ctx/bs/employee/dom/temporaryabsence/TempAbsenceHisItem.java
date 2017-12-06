/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.AfterChildbirth;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.AnyLeave;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.CareHoliday;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.ChildCareHoliday;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.GenericString;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.Leave;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.LeaveHolidayType;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.MidweekClosure;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.SickLeave;

/**
 * @author danpv Domain Name : 休職休業履歴項目
 *
 */
@Getter
public class TempAbsenceHisItem extends AggregateRoot {

	private LeaveHolidayType leaveHolidayType;

	private String historyId;

	private String employeeId;

	// ------------- Optional ----------------

	/**
	 * 備考
	 */
	private GenericString remarks;

	/**
	 * 社会保険支給対象区分
	 */
	private Integer soInsPayCategory;

	/**
	 * NoConstructor
	 */
	public TempAbsenceHisItem() {

	}

	/**
	 * @param historyId2
	 * @param employeeId2
	 * @param remarks2
	 * @param soInsPayCategory2
	 */
	public TempAbsenceHisItem(LeaveHolidayType leaveHolidayType, String historyId, String employeeId,
			GenericString remarks, Integer soInsPayCategory) {
		this.leaveHolidayType = leaveHolidayType;
		this.historyId = historyId;
		this.employeeId = employeeId;
		this.remarks = remarks;
		this.soInsPayCategory = soInsPayCategory;
	}

	public static TempAbsenceHisItem createTempAbsenceHisItem(int leaveHolidayType, String historyId, String employeeId,
			String remarks, Integer soInsPayCategory, Boolean multiple, String familyMemberId, Boolean sameFamily,
			Integer childType, GeneralDate createDate, Boolean spouseIsLeave, Integer sameFamilyDays) {
		LeaveHolidayType leaveType = EnumAdaptor.valueOf(leaveHolidayType, LeaveHolidayType.class);
		switch (leaveType) {
		case LEAVE_OF_ABSENCE:
			return Leave.init(historyId, employeeId, remarks, soInsPayCategory);
		case MIDWEEK_CLOSURE:
			return MidweekClosure.init(historyId, employeeId, remarks, soInsPayCategory, multiple);
		case AFTER_CHILDBIRTH:
			return AfterChildbirth.init(historyId, employeeId, remarks, soInsPayCategory, familyMemberId);
		case CHILD_CARE_NURSING:
			return ChildCareHoliday.init(historyId, employeeId, remarks, soInsPayCategory, sameFamily, childType,
					familyMemberId, createDate, spouseIsLeave);
		case NURSING_CARE_LEAVE:
			return CareHoliday.init(historyId, employeeId, remarks, soInsPayCategory, sameFamily, sameFamilyDays,
					familyMemberId);
		case SICK_LEAVE:
			return SickLeave.init(historyId, employeeId, remarks, soInsPayCategory);
		case ANY_LEAVE:
			return AnyLeave.init(historyId, employeeId, remarks, soInsPayCategory);
		default:
			return null;
		}

	}

	public static TempAbsenceHisItem createLeave(String historyId, String employeeId, String remarks,
			Integer soInsPayCategory) {
		return Leave.init(historyId, employeeId, remarks, soInsPayCategory);
	}

	public static TempAbsenceHisItem createMidweekClosure(String historyId, String employeeId, String remarks,
			Integer soInsPayCategory, Boolean multiple) {
		return MidweekClosure.init(historyId, employeeId, remarks, soInsPayCategory, multiple);
	}

	public static TempAbsenceHisItem createAfterChildbirth(String historyId, String employeeId, String remarks,
			Integer soInsPayCategory, String familyMemberId) {
		return AfterChildbirth.init(historyId, employeeId, remarks, soInsPayCategory, familyMemberId);
	}

	public static TempAbsenceHisItem createChildCareHoliday(String historyId, String employeeId, String remarks,
			Integer soInsPayCategory, Boolean sameFamily, Integer childType, String familyMemberId,
			GeneralDate createDate, Boolean spouseIsLeave) {
		return ChildCareHoliday.init(historyId, employeeId, remarks, soInsPayCategory, sameFamily, childType,
				familyMemberId, createDate, spouseIsLeave);
	}

	public static TempAbsenceHisItem createCareHoliday(String historyId, String employeeId, String remarks,
			Integer soInsPayCategory, Boolean sameFamily, Integer sameFamilyDays, String familyMemberId) {
		return CareHoliday.init(historyId, employeeId, remarks, soInsPayCategory, sameFamily, sameFamilyDays,
				familyMemberId);
	}

	public static TempAbsenceHisItem createSickLeave(String historyId, String employeeId, String remarks,
			Integer soInsPayCategory) {
		return SickLeave.init(historyId, employeeId, remarks, soInsPayCategory);
	}

	public static TempAbsenceHisItem createAnyLeave(String historyId, String employeeId, String remarks,
			Integer soInsPayCategory) {
		return AnyLeave.init(historyId, employeeId, remarks, soInsPayCategory);
	}

}
