package nts.uk.ctx.at.request.dom.application.common.adapter.record;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
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
	private Integer attendanceStampTimeFirst;
	
	// 終了時刻1
	private Integer leaveStampTimeFirst;
	
	// 開始時刻2
	private Integer attendanceStampTimeSecond;
	
	// 終了時刻2
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
	
	/**
	 * 計算フレックス
	 */
	private Integer flexCaculation;
	
	/**
	 * 計算残業
	 */
	private List<OvertimeInputCaculation> overtimeCaculation;
	
	/**
	 * 計算振替残業
	 */
	private List<OvertimeInputCaculation> overtimeOverHeadCaculation;
	/**
	 * 計算休日出勤
	 */
	private List<OvertimeInputCaculation> overtimeHolidayCaculation;
	/**
	 * 計算振替
	 */
	private List<OvertimeInputCaculation> overtimeTransferCaculation;
	/**
	 * 計算就業外深夜
	 */
	private Integer shiftNightCaculation;
	/**
	 * 休出深夜
	 */
	private Integer breakLateNight;
}
