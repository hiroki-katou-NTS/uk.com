package nts.uk.ctx.bs.employee.dom.temporaryabsence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.AfterChildbirth;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.CareHoliday;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.ChildCareHoliday;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.Leave;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.LeaveHolidayState;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.MidweekClosure;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.TempAbsenceType;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 休職休業
 * 
 * @author xuan vinh
 */
@Getter
@NoArgsConstructor
@Setter
public class TemporaryAbsence extends AggregateRoot {

	/**
	 * 休職休業区分
	 */
	private LeaveHolidayState leaveHolidayState;

	/**
	 * 休職休業ID
	 */
	private String tempAbsenceId;

	/**
	 * 社員ID
	 */
	private String employeeId;

	/**
	 * 期間
	 */
	private DateHistoryItem dateHistoryItem;
	
	public static TemporaryAbsence createSimpleFromJavaType(String employeeId, String tempAbsenceId, int tempAbsenceType, String histId,
			GeneralDate startDate, GeneralDate endDate, String tempAbsenceReason, String familyMemberId, GeneralDate birthDate, int multiple){
		TempAbsenceType type = EnumAdaptor.valueOf(tempAbsenceType, TempAbsenceType.class);
		DateHistoryItem dateHistoryItem = new DateHistoryItem(histId, new DatePeriod(startDate, endDate));
		switch (type) {
		case LEAVE_OF_ABSENCE:
			Leave leave = new Leave(tempAbsenceReason);
			return new TemporaryAbsence(leave, tempAbsenceId, employeeId, dateHistoryItem);
		case MIDWEEK_CLOSURE:
			MidweekClosure midweekClosure = new MidweekClosure(birthDate, multiple);
			return new TemporaryAbsence(midweekClosure, tempAbsenceId, employeeId, dateHistoryItem);
		case AFTER_CHILDBIRTH:
			AfterChildbirth afterChildbirth = new AfterChildbirth(familyMemberId);
			return new TemporaryAbsence(afterChildbirth, tempAbsenceId, employeeId, dateHistoryItem);
		case CHILD_CARE_NURSING:
			ChildCareHoliday childCareHoliday = new ChildCareHoliday(familyMemberId);
			return new TemporaryAbsence(childCareHoliday, tempAbsenceId, employeeId, dateHistoryItem);
		case NURSING_CARE_LEAVE:
			CareHoliday careHoliday = new CareHoliday(familyMemberId);
			return new TemporaryAbsence(careHoliday, tempAbsenceId, employeeId, dateHistoryItem);
		default:
			return null;
		}
	}

	/**
	 * @param leaveHolidayState
	 * @param tempAbsenceId
	 * @param employeeId
	 * @param dateHistoryItem
	 */
	public TemporaryAbsence(LeaveHolidayState leaveHolidayState, String tempAbsenceId, String employeeId,
			DateHistoryItem dateHistoryItem) {
		super();
		this.leaveHolidayState = leaveHolidayState;
		this.tempAbsenceId = tempAbsenceId;
		this.employeeId = employeeId;
		this.dateHistoryItem = dateHistoryItem;
	}
	

}
