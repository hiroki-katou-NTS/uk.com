package nts.uk.file.at.app.export.attendancerecord.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class AttendanceRecordReportColumnData.
 */

@Getter
@Setter
@AllArgsConstructor
public class AttendanceRecordReportColumnData {
	
	/** The uper. */
	private String uper;
	
	/** The align uper. */
	private boolean alignUper = false;
	
	/** The lower. */
	private String lower;
	
	/** The align lower. */
	private boolean alignLower = false;
}
