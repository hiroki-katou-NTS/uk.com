package nts.uk.file.at.app.export.attendancerecord;

import lombok.Data;

@Data
public class MonthlyResultCheck {
	//	雇用取得結果
	// false(NG)(取得できなかった)、true(OK)(取得できた)
	private boolean employeeResult;
	
	//	チェック結果
	// false(NG)(不一致)、true(OK)(一致)
	private boolean checkResult;
}
