package nts.uk.file.at.app.export.yearholidaymanagement;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputYearHolidayManagementQuery {
	// 対象期間
	int selectedDateType;
	// 参照区分
	int selectedReferenceType;
	// 参照区分
	Integer printDate;
	// 改ページ区分
	int pageBreakSelected;
	//selectedEmployees
	List<EmployeeInfo>  selectedEmployees;
}
