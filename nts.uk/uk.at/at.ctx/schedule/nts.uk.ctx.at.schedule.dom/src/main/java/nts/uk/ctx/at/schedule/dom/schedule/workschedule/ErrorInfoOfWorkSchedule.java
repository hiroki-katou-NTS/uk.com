package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.Optional;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 勤務予定のエラー情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定.勤務予定のエラー情報
 * @author dan_pv
 *
 */
@Value
public class ErrorInfoOfWorkSchedule {
	
	/** 社員ID */
	private String employeeId;
	
	/** 年月日 */
	private GeneralDate date;
	
	/** 項目Id */
	private Optional<Integer> attendanceItemId;
	
	/** エラーメッセージ */
	private String errorMessage;
	
	/**
	 * 事前条件のエラー
	 * @param employeeId 社員ID
	 * @param date 年月日
	 * @param errorMessage エラーメッセージ
	 * @return
	 */
	public static ErrorInfoOfWorkSchedule preConditionError(
			String employeeId, GeneralDate date, String errorMessage) {
		
		return new ErrorInfoOfWorkSchedule(employeeId, date, Optional.empty(), errorMessage);
	}
	
	/**
	 * 勤怠項目のエラー
	 * @param employeeId 社員ID
	 * @param date 年月日
	 * @param attendanceItemId 項目ID
	 * @param errorMessage エラーメッセージ
	 * @return
	 */
	public static ErrorInfoOfWorkSchedule attendanceItemError(
			String employeeId, GeneralDate date, int attendanceItemId, String errorMessage) {
		
		return new ErrorInfoOfWorkSchedule(employeeId, date, Optional.of(attendanceItemId), errorMessage);
	}

}
