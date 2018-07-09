package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AttendanceItemCondition;

/**
 * 月別実績をチェックする
 * @author tutk
 *
 */
public interface PerTimeMonActualResultService {
	boolean checkPerTimeMonActualResult(YearMonth yearMonth,int closureID, Integer closureDate,String employeeID,AttendanceItemCondition attendanceItemCondition);
}
