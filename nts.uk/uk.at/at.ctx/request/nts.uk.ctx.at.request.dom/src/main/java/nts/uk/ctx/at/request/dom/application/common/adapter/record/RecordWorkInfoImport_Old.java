package nts.uk.ctx.at.request.dom.application.common.adapter.record;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class RecordWorkInfoImport_Old {
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
	
	/** 日別実績の勤怠時間．実働時間．総労働時間．遅刻時間．遅刻時間 */
	private Integer lateTime1;
	
	/** 日別実績の勤怠時間．実働時間．総労働時間．早退時間．早退時間 */
	private Integer leaveEarlyTime1;
	
	/** 日別実績の勤怠時間．実働時間．総労働時間．遅刻時間．遅刻時間 */
	private Integer lateTime2;
	
	/** 日別実績の勤怠時間．実働時間．総労働時間．早退時間．早退時間 */
	private Integer leaveEarlyTime2;

	/** 日別実績の勤怠時間．実働時間．総労働時間．短時間勤務時間.育児時間 */
	private Integer childCareTime;
	
	/** 日別実績の勤怠時間．実働時間．総労働時間.外出時間.計上用合計時間.合計時間.外出時間 私用 */
	private Integer outingTimePrivate;
	
	/** 日別実績の勤怠時間．実働時間．総労働時間.外出時間.計上用合計時間.合計時間.外出時間 組合 */
	private Integer outingTimeCombine;

	/** 日別実績の勤怠時間．実績時間．総労働時間．所定外時間．残業時間．フレックス時間．フレックス時間.計算フレックス */
	private Integer flexTime;
	
	/** 日別実績の勤怠時間．実績時間．総労働時間．所定外時間．残業時間．残業枠時間．残業時間.計算残業 */
	/** 日別実績の勤怠時間．実績時間．総労働時間．所定外時間．残業時間．残業枠時間．振替時間.計算振替残業 */
	private List<CommonTimeSheetImport> overtimes;
		
//		private List<CommonTimeSheet> otTranferTimes;
		
	/** 日別実績の勤怠時間．実績時間．総労働時間．所定外時間．休出時間．休出枠時間．休出時間.計算休日出勤 */
	/** 日別実績の勤怠時間．実績時間．総労働時間．所定外時間．休出時間．休出枠時間．振替時間.計算振替 */
	private List<CommonTimeSheetImport> holidayWorks;
		
//		private List<CommonTimeSheet> holWorkTranferTimes;
		
	/** 計算就業外深夜 */
	/** 休出深夜 */
	private Integer midnightTime;
}
