package nts.uk.ctx.at.request.dom.application;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
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
	@Setter
	private ReflectedState actualReflectStatus;
	
	/**
	 * 予定反映状態
	 */
	@Setter
	private ReflectedState scheReflectStatus;
	
	/**
	 * 対象日
	 */
	private GeneralDate targetDate;
	
	/**
	 * 申請反映の更新状態
	 */
	private Optional<DailyAttendanceUpdateStatus> opUpdateStatusAppReflect;
	
	/**
	 * 申請取消の更新状態
	 */
	private Optional<DailyAttendanceUpdateStatus> opUpdateStatusAppCancel;
	
	public ReflectionStatusOfDay(ReflectedState actualReflectStatus, ReflectedState scheReflectStatus, GeneralDate targetDate,
			Optional<DailyAttendanceUpdateStatus> opUpdateStatusAppReflect, Optional<DailyAttendanceUpdateStatus> opUpdateStatusAppCancel) {
		this.actualReflectStatus = actualReflectStatus;
		this.scheReflectStatus = scheReflectStatus;
		this.targetDate = targetDate;
		this.opUpdateStatusAppReflect = opUpdateStatusAppReflect;
		this.opUpdateStatusAppCancel = opUpdateStatusAppCancel;
	} 
	
	public static ReflectionStatusOfDay createNew(ReflectedState actualReflectStatus, ReflectedState scheReflectStatus, GeneralDate targetDate) {
		return new ReflectionStatusOfDay(actualReflectStatus, scheReflectStatus, targetDate, Optional.empty(), Optional.empty());
	}
	
}
