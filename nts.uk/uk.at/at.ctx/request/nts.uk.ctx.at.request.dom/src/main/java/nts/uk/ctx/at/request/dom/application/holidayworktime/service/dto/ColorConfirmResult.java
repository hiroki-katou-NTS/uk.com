package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculationImport;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorResult;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ColorConfirmResult {
	
	private boolean isConfirm;
	
	private Integer errorCD;
	
	private Integer frameNo;
	
	private String msgID;
	
	private List<String> params;
	
	private DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport;
	
	private PreActualColorResult preActualColorResult;
	
}
