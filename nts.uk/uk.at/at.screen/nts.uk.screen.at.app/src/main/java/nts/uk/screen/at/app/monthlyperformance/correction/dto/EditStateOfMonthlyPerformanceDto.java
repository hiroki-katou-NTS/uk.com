package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Data
@AllArgsConstructor
public class EditStateOfMonthlyPerformanceDto {

	private String employeeId;

	/** The attendance item id. */
	// 勤怠項目ID
	private Integer attendanceItemId;
	
	// 期間
	private DatePeriod datePeriod;
	
	// 処理年月
	private int processDate;
	
	// 締めID
	private int closureID;
	
	// 締め日
	private ClosureDateDto closureDate;
	
	// 編集状態
	// 0 : 手修正（本人） MySelf , 1 : 手修正（他人） Others
	private Integer stateOfEdit;
	
}
