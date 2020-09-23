package nts.uk.file.at.app.export.attendancerecord;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.file.at.app.export.dailyschedule.ActualValue;

@Data
public class MonthlyOutputAttendanceRecord {
	
	private long position;
	
	//	列番号
	private String columnIndex;
	
	//	実績値
	private List<ActualValue> actualValue;
	
	//	属性
	private String attribute;
	
	//	年月
	private YearMonth yearMonth;
	
	//	社員ID
	private String employeeId;
	
	//	年月日
	private GeneralDate date;

	//	編集済み実績値
	private String editedAchievementValue;
}
