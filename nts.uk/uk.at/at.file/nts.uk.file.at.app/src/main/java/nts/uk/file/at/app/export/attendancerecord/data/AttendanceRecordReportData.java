package nts.uk.file.at.app.export.attendancerecord.data;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class AttendanceRecordReportData.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRecordReportData {

	/** The company name. */
	private String companyName = "日通システム　株式会社";

	/** The report name. */
	private String reportName = "出勤簿";

	/** The export date time. */
	private String exportDateTime = "2018/3/26　15:14:23";

	/** The monthly header. */
	private List<AttendanceRecordReportColumnData> monthlyHeader;
	
	/** The daily header. */
	private List<AttendanceRecordReportColumnData> dailyHeader;
	
	/** The seal col name. */
	private List<String> sealColName;
	
	/** The report data. */
	private Map<String, List<AttendanceRecordReportEmployeeData>> reportData;
}
