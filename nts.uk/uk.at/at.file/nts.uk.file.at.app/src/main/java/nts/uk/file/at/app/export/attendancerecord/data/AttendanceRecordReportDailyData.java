package nts.uk.file.at.app.export.attendancerecord.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class AttendanceRecordReportDailyData.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecordReportDailyData {
	
	/** The date. */
	private String date;
	
	/** The day of week. */
	private String dayOfWeek;
	
	/** The daily data. */
	private List<AttendanceRecordReportColumnData> columnDatas;
	
	/** The is second col. */
	private boolean isSecondCol;
}
