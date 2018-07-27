package nts.uk.file.at.app.export.attendancerecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportData;

/**
 * The Class AttendanceRecordReportDatasource.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecordReportDatasource {
	
	/** The data. */
	private AttendanceRecordReportData data;
	
	/** The mode. */
	private int mode;
}
