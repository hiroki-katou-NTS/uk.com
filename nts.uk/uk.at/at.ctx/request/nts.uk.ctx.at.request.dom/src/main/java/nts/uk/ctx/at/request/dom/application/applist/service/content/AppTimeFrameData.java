package nts.uk.ctx.at.request.dom.application.applist.service.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;

/**
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.A:申請一覧画面移動前BackupCopy.アルゴリズム.パラメータ.申請時間データ（Frame）
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppTimeFrameData {
	/**
	 * 開始時間
	 */
	private Integer startTime;
	
	/**
	 * 勤怠項目NO
	 */
	private int attendanceNo;
	
	/**
	 * 勤怠種類
	 */
	private AttendanceType_Update attendanceType;
	
	/**
	 * 勤怠名称
	 */
	private String attendanceName;
	
	/**
	 * 終了時間
	 */
	private Integer endTime;
	
	/**
	 * 申請時間
	 */
	private int applicationTime;
}
