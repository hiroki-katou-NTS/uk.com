package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AffEmploymentHistoryDto {

	/** The employment code. */
	// 雇用コード
	private String employmentCode;
	
	/** The period. */
	// 期間
	private DatePeriod period;

	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	public AffEmploymentHistoryDto(String employmentCode, String employeeId){
		this.employmentCode = employmentCode;
		this.employeeId = employeeId;
	}
	
}
