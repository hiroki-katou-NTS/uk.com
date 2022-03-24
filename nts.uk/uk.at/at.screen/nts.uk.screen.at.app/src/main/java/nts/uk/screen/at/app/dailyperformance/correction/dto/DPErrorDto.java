/**
 * 5:16:26 PM Aug 22, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;

/**
 * @author hungnm
 *
 */
@Data
@AllArgsConstructor
public class DPErrorDto {
	private String errorCode;
	private String errorType;
	private String employeeId;
	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate processingDate;
	private List<Integer> attendanceItemId;
	private boolean errorCancelable;
	private String errorAlarmMessage;
	private String id;
	
	public DPErrorDto(String errorCode, String errorType, String employeeId, GeneralDate processingDate,
			List<Integer> attendanceItemId, boolean errorCancelable, String errorAlarmMessage) {
		super();
		this.errorCode = errorCode;
		this.errorType = errorType;
		this.employeeId = employeeId;
		this.processingDate = processingDate;
		this.attendanceItemId = attendanceItemId;
		this.errorCancelable = errorCancelable;
		this.errorAlarmMessage = errorAlarmMessage;
	}
	
}
