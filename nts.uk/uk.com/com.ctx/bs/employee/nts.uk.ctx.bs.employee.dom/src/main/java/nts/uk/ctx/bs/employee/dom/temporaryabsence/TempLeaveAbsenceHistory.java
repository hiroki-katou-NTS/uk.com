package nts.uk.ctx.bs.employee.dom.temporaryabsence;

import java.util.List;

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
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.MidweekClosure;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.LeaveHolidayType;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * Domain Name: 休職休業履歴
 * 
 * @author xuan vinh
 * @author danpv
 */
@Getter
@NoArgsConstructor
@Setter
public class TempLeaveAbsenceHistory extends AggregateRoot {
	
	private String tempLeaveAbsenceHisId;

	/**
	 * 社員ID
	 */
	private String employeeId;

	/**
	 * 期間
	 */
	private List<DateHistoryItem> dateHistoryItems;
	
	public static TempLeaveAbsenceHistory createSimpleFromJavaType(String employeeId, String tempAbsenceId, int tempAbsenceType, String histId,
			GeneralDate startDate, GeneralDate endDate, String tempAbsenceReason, String familyMemberId, GeneralDate birthDate, int multiple){
		LeaveHolidayType type = EnumAdaptor.valueOf(tempAbsenceType, LeaveHolidayType.class);
		DateHistoryItem dateHistoryItem = new DateHistoryItem(histId, new DatePeriod(startDate, endDate));
		/*switch (type) {
		case LEAVE_OF_ABSENCE:
			Leave leave = new Leave(tempAbsenceReason);
			return new TempLeaveAbsenceHistory(leave, tempAbsenceId, employeeId, dateHistoryItem);
		case MIDWEEK_CLOSURE:
			MidweekClosure midweekClosure = new MidweekClosure(birthDate, multiple);
			return new TempLeaveAbsenceHistory(midweekClosure, tempAbsenceId, employeeId, dateHistoryItem);
		case AFTER_CHILDBIRTH:
			AfterChildbirth afterChildbirth = new AfterChildbirth(familyMemberId);
			return new TempLeaveAbsenceHistory(afterChildbirth, tempAbsenceId, employeeId, dateHistoryItem);
		case CHILD_CARE_NURSING:
			ChildCareHoliday childCareHoliday = new ChildCareHoliday(familyMemberId);
			return new TempLeaveAbsenceHistory(childCareHoliday, tempAbsenceId, employeeId, dateHistoryItem);
		case NURSING_CARE_LEAVE:
			CareHoliday careHoliday = new CareHoliday(familyMemberId);
			return new TempLeaveAbsenceHistory(careHoliday, tempAbsenceId, employeeId, dateHistoryItem);
		default:
			return null;
		}*/
		return null;
	}


}
