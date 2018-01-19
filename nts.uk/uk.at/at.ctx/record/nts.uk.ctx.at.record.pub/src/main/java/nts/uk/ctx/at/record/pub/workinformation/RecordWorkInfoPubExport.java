package nts.uk.ctx.at.record.pub.workinformation;

import lombok.Getter;

/**
 * 
 * @author nampt
 * 
 */
@Getter
public class RecordWorkInfoPubExport {

	// 勤務種類コード
	private String workTypeCode;
	
	// 就業時間帯コード
	private String workTimeCode;
	
	// 日別実績の出退勤．出退勤．出勤．打刻 , 勤務NO＝0
	private Integer attendanceStampTimeFirst;
	
	// 日別実績の出退勤．出退勤．退勤．打刻, 勤務NO＝0
	private Integer leaveStampTimeFirst;
	
	// 日別実績の出退勤．出退勤．出勤．打刻 , 勤務NO＝1
	private Integer attendanceStampTimeSecond;
	
	// 日別実績の出退勤．出退勤．退勤．打刻, 勤務NO＝1
	private Integer leaveStampTimeSecond;
	
	// 日別実績の勤怠時間．実働時間．総労働時間．遅刻時間．遅刻時間
	private Integer time1;
	
	// 日別実績の勤怠時間．実働時間．総労働時間．早退時間．早退時間
	private Integer time2;
	
	// 日別実績の勤怠時間．実働時間．総労働時間．遅刻時間．遅刻時間
	private Integer time3;
	
	// 日別実績の勤怠時間．実働時間．総労働時間．早退時間．早退時間
	private Integer time4;

	//日別実績の勤怠時間．実働時間．総労働時間．短時間勤務時間
	private Integer time5;

	public RecordWorkInfoPubExport(String workTypeCode, String workTimeCode, Integer attendanceStampTimeFirst,
			Integer leaveStampTimeFirst, Integer attendanceStampTimeSecond, Integer leaveStampTimeSecond, Integer time1, Integer time2,
			Integer time3, Integer time4, Integer time5) {
		super();
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
		this.attendanceStampTimeFirst = attendanceStampTimeFirst;
		this.leaveStampTimeFirst = leaveStampTimeFirst;
		this.attendanceStampTimeSecond = attendanceStampTimeSecond;
		this.leaveStampTimeSecond = leaveStampTimeSecond;
		this.time1 = time1;
		this.time2 = time2;
		this.time3 = time3;
		this.time4 = time4;
		this.time5 = time5;
	}
	
}
