package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AttendanceItemCondition;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 月別実績をチェックする
 * @author tutk
 *
 */
public interface PerTimeMonActualResultService {
	Map<String, Integer> checkPerTimeMonActualResult(YearMonth yearMonth, Optional<ClosureId> closureID,
			Optional<ClosureDate> closureDate, String employeeID, AttendanceItemCondition attendanceItemCondition,
			List<Integer> attendanceIds);
}
	