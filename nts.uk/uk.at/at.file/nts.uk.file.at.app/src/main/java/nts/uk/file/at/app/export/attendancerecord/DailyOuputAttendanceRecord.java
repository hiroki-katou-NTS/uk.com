package nts.uk.file.at.app.export.attendancerecord;

import java.util.List;

import lombok.Data;
import nts.uk.file.at.app.export.dailyschedule.ActualValue;

@Data
public class DailyOuputAttendanceRecord {
	//	列番号
	private String columnIndex;
	
	//	実績値
	private List<ActualValue> actualValue;
	
	//	属性
	private String attribute;
	
	//	年月日
	private String date;
	
	//	社員ID
	private String employeeId;
	
	//	編集済み実績値
	private String editedAchievementValue;
}
