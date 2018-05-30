package nts.uk.file.at.app.export.attendancerecord.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class AttendanceRecordReportWeeklySumaryData.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecordReportWeeklySumaryData {
	
	/** The date range. */
	private String dateRange;
	
	/** The is second col. */
	private boolean isSecondCol;
	
	/** The sumary data. */
	private List<AttendanceRecordReportColumnData> columnDatas;
}
