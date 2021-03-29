package nts.uk.ctx.at.shared.dom.application.common;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 対象日の反映状態
 * @author thanh_nx
 *
 */
@Getter
public class ReflectionStatusOfDayShare {
	
	/**
	 * 実績反映状態
	 */
	@Setter
	private ReflectedStateShare actualReflectStatus;
	
	/**
	 * 予定反映状態
	 */
	private ReflectedStateShare scheReflectStatus;
	
	/**
	 * 対象日
	 */
	private GeneralDate targetDate;
	
	/**
	 * 申請反映の更新状態
	 */
	private Optional<DailyAttendanceUpdateStatusShare> opUpdateStatusAppReflect;
	
	/**
	 * 申請取消の更新状態
	 */
	private Optional<DailyAttendanceUpdateStatusShare> opUpdateStatusAppCancel;
	
	public ReflectionStatusOfDayShare(ReflectedStateShare actualReflectStatus, ReflectedStateShare scheReflectStatus, GeneralDate targetDate,
			Optional<DailyAttendanceUpdateStatusShare> opUpdateStatusAppReflect, Optional<DailyAttendanceUpdateStatusShare> opUpdateStatusAppCancel) {
		this.actualReflectStatus = actualReflectStatus;
		this.scheReflectStatus = scheReflectStatus;
		this.targetDate = targetDate;
		this.opUpdateStatusAppReflect = opUpdateStatusAppReflect;
		this.opUpdateStatusAppCancel = opUpdateStatusAppCancel;
	} 
	
	public static ReflectionStatusOfDayShare createNew(ReflectedStateShare actualReflectStatus, ReflectedStateShare scheReflectStatus, GeneralDate targetDate) {
		return new ReflectionStatusOfDayShare(actualReflectStatus, scheReflectStatus, targetDate, Optional.empty(), Optional.empty());
	}
	
}
