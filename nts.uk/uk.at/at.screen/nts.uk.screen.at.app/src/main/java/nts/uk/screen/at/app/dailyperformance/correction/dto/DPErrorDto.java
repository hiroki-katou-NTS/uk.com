/**
 * 5:16:26 PM Aug 22, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

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
	private GeneralDate processingDate;
	private List<Integer> attendanceItemId;
	private boolean errorCancelable;
	private String errorAlarmMessage;
}
