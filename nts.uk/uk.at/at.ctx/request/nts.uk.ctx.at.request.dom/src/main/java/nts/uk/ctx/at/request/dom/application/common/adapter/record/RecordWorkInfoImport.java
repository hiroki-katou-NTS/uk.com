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
	
	// 開始時刻1
	private int attendanceStampTimeFirst;
	
	// 終了時刻1
	private int leaveStampTimeFirst;
	
	// 開始時刻2
	private int attendanceStampTimeSecond;
	
	// 終了時刻2
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
