package nts.uk.ctx.at.request.dom.application.overtime.service.output;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.UseAtr;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class RecordWorkOutput {
	
	private UseAtr recordWorkDisplay;
	
	// 日別実績の出退勤．出退勤．出勤．打刻 , 勤務NO＝0
	private Integer startTime1;
	
	// 日別実績の出退勤．出退勤．退勤．打刻, 勤務NO＝0
	private Integer endTime1;
	
	// 日別実績の出退勤．出退勤．出勤．打刻 , 勤務NO＝1
	private Integer startTime2;
	
	// 日別実績の出退勤．出退勤．退勤．打刻, 勤務NO＝1
	private Integer endTime2;
	
}
