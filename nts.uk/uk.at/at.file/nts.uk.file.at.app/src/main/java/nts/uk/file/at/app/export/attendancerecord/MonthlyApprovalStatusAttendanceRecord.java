package nts.uk.file.at.app.export.attendancerecord;

import lombok.Data;
import nts.arc.time.YearMonth;

@Data
public class MonthlyApprovalStatusAttendanceRecord {
	//	年月(YM)
	private YearMonth ym;
	//	承認済フラグ
	private boolean approvedFlag;
	//	社員ID
	private String employeeId;
	//	締めID
	private int closureId;
	//	締め日(YMD)
	private YearMonth ymd;
}
