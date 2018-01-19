package nts.uk.ctx.at.record.app.find.dailyperform.erroralarm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ConvertibleAttendanceItem;

@AttendanceItemRoot(rootName = "社員の日別実績エラー一覧")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDailyPerErrorDto implements ConvertibleAttendanceItem {

	//TODO: item id not map
	
	/** 会社ID: 会社ID */
	private String companyID;

	/** エラー発生社員: 社員ID */
	private String employeeID;

	/** 処理年月日: 年月日 */
	private GeneralDate date;

	/** エラー: 勤務実績のエラーアラームコード */
//	@AttendanceItemLayout(layout = "A", jpPropertyName = "")
//	@AttendanceItemValue()
	private String errorCode;

	/** 項目一覧: 勤怠項目ID */
	//TODO: set list max value
//	@AttendanceItemLayout(layout = "A", jpPropertyName = "", isList = true, listMaxLength = ?)
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private List<Integer> attendanceItemList;
	
	public  static EmployeeDailyPerErrorDto getDto(EmployeeDailyPerError domain){
		EmployeeDailyPerErrorDto dto = new EmployeeDailyPerErrorDto();
		if(domain != null){
			dto.setAttendanceItemList(domain.getAttendanceItemList());
			dto.setCompanyID(domain.getCompanyID());
			dto.setDate(domain.getDate());
			dto.setEmployeeID(domain.getEmployeeID());
			dto.setErrorCode(domain.getErrorAlarmWorkRecordCode().v());
		}
		return dto;
	}
}
