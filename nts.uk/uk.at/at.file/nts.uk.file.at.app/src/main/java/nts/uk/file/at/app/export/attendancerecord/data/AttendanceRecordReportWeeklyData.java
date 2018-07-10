package nts.uk.file.at.app.export.attendancerecord.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class AttendanceRecordReportWeeklyData.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecordReportWeeklyData {
	
	/** The daily datas. */
	private List<AttendanceRecordReportDailyData> dailyDatas;
	
	/** The weekly sumary data. */
	private AttendanceRecordReportWeeklySumaryData weeklySumaryData;
}
