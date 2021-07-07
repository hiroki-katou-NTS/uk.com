package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CorrectSupportData {
	
	private List<OuenWorkTimeSheetOfDailyAttendance> attendance;
	
	private WorkTemporary informationWork;
}
