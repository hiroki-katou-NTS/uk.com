package nts.uk.ctx.at.request.dom.application;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.対象日の反映状態
 * @author Doan Duy Hung
 *
 */
@Getter
public class ReflectionStatusOfDay {
	
	/**
	 * 実績反映状態
	 */
	private ReflectedState resultsReflectedStatus;
	
	/**
	 * 予定反映状態
	 */
	private ReflectedState scheduleReflectionStatus;
	
	/**
	 * 対象日
	 */
	private GeneralDate targetDate;
	
	/**
	 * 申請反映の更新状態
	 */
	private Optional<DailyAttendanceUpdateStatus> opUpdateStatusAppReflection;
	
	/**
	 * 申請取消の更新状態
	 */
	private Optional<DailyAttendanceUpdateStatus> opUpdateStatusAppCancellation;
	
	public ReflectionStatusOfDay(ReflectedState resultsReflectedStatus, ReflectedState scheduleReflectionStatus, GeneralDate targetDate,
			Optional<DailyAttendanceUpdateStatus> opUpdateStatusAppReflection, Optional<DailyAttendanceUpdateStatus> opUpdateStatusAppCancellation) {
		this.resultsReflectedStatus = resultsReflectedStatus;
		this.scheduleReflectionStatus = scheduleReflectionStatus;
		this.targetDate = targetDate;
		this.opUpdateStatusAppReflection = opUpdateStatusAppReflection;
		this.opUpdateStatusAppCancellation = opUpdateStatusAppCancellation;
	} 
	
}
