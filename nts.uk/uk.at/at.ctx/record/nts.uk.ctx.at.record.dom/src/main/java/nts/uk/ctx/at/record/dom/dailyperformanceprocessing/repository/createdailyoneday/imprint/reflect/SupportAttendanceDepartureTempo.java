package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect;
/**
 * 出退勤の応援
 * @author phongtq
 *
 */

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupportAttendanceDepartureTempo {
	/** 最初の出勤 */
	private Optional<OuenWorkTimeSheetOfDailyAttendance> firstAttendance;
	/** 最後の退勤 */
	private Optional<OuenWorkTimeSheetOfDailyAttendance> lastLeave;
}
