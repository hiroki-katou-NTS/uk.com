package nts.uk.file.at.app.export.attendancerecord.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.file.at.app.export.dailyschedule.ActualValue;

/**
 * The Class AttendanceRecordReportDailyData.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecordReportDailyData {
	
//	社員ID
	private String employeeId;
	
	//	年月日
	private GeneralDate date;
	
	//	上段/下段
	private List<AttendanceRecordReportColumnData> position;

	//	列番号
	private String columnIndex;
	
	//	属性
	private String attribute;
	
	//	実績値
	private List<ActualValue> actualValue;

	//	編集済み実績値
	private String editedAchievementValue;
}
