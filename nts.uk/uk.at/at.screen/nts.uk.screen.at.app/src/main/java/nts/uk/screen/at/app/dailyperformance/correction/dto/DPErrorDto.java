/**
 * 5:16:26 PM Aug 22, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
@Data
public class DPErrorDto {
	private String errorCode;
	private String errorType;
	private String employeeId;
	private GeneralDate processingDate;
	private Integer attendanceItemId;
	private boolean errorCancelable;
	public DPErrorDto(String errorCode, String errorType, String employeeId, GeneralDate processingDate,
			Integer attendanceItemId) {
		super();
		this.errorCode = errorCode;
		this.errorType = errorType;
		this.employeeId = employeeId;
		this.processingDate = processingDate;
		this.attendanceItemId = attendanceItemId;
		this.errorCancelable = false;
	}
	
	
}
