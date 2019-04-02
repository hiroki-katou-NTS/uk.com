package nts.uk.file.at.app.export.yearholidaymanagement;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;

@Data
@NoArgsConstructor
public class OutputYearHolidayManagementQuery {
	String programName;
	GeneralDateTime exportTime;
	// 対象期間
	int selectedDateType;
	// 参照区分
	int selectedReferenceType;
	// 指定月
	Integer printDate;
	// 参照の選択
	int pageBreakSelected;
	//selectedEmployees
	List<EmployeeInfo>  selectedEmployees;
	
	List<ClosurePrintDto> closureData;
}
