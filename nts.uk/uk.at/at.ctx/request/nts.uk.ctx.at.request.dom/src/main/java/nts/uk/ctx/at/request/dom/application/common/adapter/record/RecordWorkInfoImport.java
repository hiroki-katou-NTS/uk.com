package nts.uk.ctx.at.request.dom.application.common.adapter.record;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class RecordWorkInfoImport {
	// 勤務種類コード
	private String workTypeCode;
	
	// 就業時間帯コード
	private String workTimeCode;
	
	// 日別実績の出退勤．出退勤．出勤．打刻 , 勤務NO＝0
	private int attendanceStampTimeFirst;
	
	// 日別実績の出退勤．出退勤．退勤．打刻, 勤務NO＝0
	private int leaveStampTimeFirst;
	
	// 日別実績の出退勤．出退勤．出勤．打刻 , 勤務NO＝1
	private int attendanceStampTimeSecond;
	
	// 日別実績の出退勤．出退勤．退勤．打刻, 勤務NO＝1
	private int leaveStampTimeSecond;
	
	// 日別実績の勤怠時間．実働時間．総労働時間．遅刻時間．遅刻時間
	private int time1;
	
	// 日別実績の勤怠時間．実働時間．総労働時間．早退時間．早退時間
	private int time2;
	
	// 日別実績の勤怠時間．実働時間．総労働時間．遅刻時間．遅刻時間
	private int time3;
	
	// 日別実績の勤怠時間．実働時間．総労働時間．早退時間．早退時間
	private int time4;

	//日別実績の勤怠時間．実働時間．総労働時間．短時間勤務時間
	private int time5;
}
