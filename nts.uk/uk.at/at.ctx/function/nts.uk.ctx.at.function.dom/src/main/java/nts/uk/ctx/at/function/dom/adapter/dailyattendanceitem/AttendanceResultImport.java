package nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResultImport {

	private String employeeId;
	
	private GeneralDate workingDate;
	
	private List<AttendanceItemValueImport> attendanceItems;
}
