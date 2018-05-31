package nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendanceitem;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Anh.Bd
 *
 */
@Value
@AllArgsConstructor
public class AttendanceResultImport {
	private String employeeId;
	
	private GeneralDate workingDate;
	
	private List<AttendanceItemValueImport> attendanceItems;
}
