package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.overtime.service.AppOvertimeReference;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class RecordWorkDto {
	
	// 日別実績の出退勤．出退勤．出勤．打刻 , 勤務NO＝0
	private Integer startTime1;
	
	// 日別実績の出退勤．出退勤．退勤．打刻, 勤務NO＝0
	private Integer endTime1;
	
	// 日別実績の出退勤．出退勤．出勤．打刻 , 勤務NO＝1
	private Integer startTime2;
	
	// 日別実績の出退勤．出退勤．退勤．打刻, 勤務NO＝1
	private Integer endTime2;
	
	// appOvertimeReference
	private AppOvertimeReference appOvertimeReference;
}
