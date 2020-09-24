package nts.uk.file.at.app.export.attendancerecord;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportFontSize;

/**
 * The Class AttendanceRecordRequest.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecordRequest {

	/** The employee code list. */
	private List<Employee> employeeList;

	/** The start date. */
	private GeneralDate startDate;

	/** The end date. */
	private GeneralDate endDate;

	/** The code */
	private long layout;
	
	/** The file type. */
	private int mode;
	
	/** The closure id. */
	private int closureId;
	
	private int fontSize;
	
	/** The condition. */
	private AttendanceRecordOutputConditionsDto condition;
	
	
}
