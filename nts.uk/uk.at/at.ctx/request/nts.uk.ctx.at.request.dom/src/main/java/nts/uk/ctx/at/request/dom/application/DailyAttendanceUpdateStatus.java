package nts.uk.ctx.at.request.dom.application;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDateTime;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.日別勤怠の更新状態
 * @author Doan Duy Hung
 *
 */
@Getter
public class DailyAttendanceUpdateStatus {
	
	/**
	 * 実績反映日時
	 */
	private Optional<GeneralDateTime> opActualReflectDateTime;
	
	/**
	 * 予定反映日時
	 */
	private Optional<GeneralDateTime> opScheReflectDateTime;
	
	/**
	 * 実績反映不可理由
	 */
	private Optional<ReasonNotReflectDaily> opReasonActualCantReflect;
	
	/**
	 * 予定反映不可理由
	 */
	private Optional<ReasonNotReflect> opReasonScheCantReflect;
	
	public DailyAttendanceUpdateStatus(Optional<GeneralDateTime> opActualReflectDateTime, Optional<GeneralDateTime> opScheReflectDateTime,
			Optional<ReasonNotReflectDaily> opReasonActualCantReflect, Optional<ReasonNotReflect> opReasonScheCantReflect) {
		this.opActualReflectDateTime = opActualReflectDateTime;
		this.opScheReflectDateTime = opScheReflectDateTime;
		this.opReasonActualCantReflect = opReasonActualCantReflect;
		this.opReasonScheCantReflect = opReasonScheCantReflect;
	}
	
	public static DailyAttendanceUpdateStatus createNew(GeneralDateTime opActualReflectDateTime, GeneralDateTime opScheReflectDateTime,
			Integer opReasonActualCantReflect, Integer opReasonScheCantReflect) {
		return new DailyAttendanceUpdateStatus(
				opActualReflectDateTime == null ? Optional.empty() : Optional.of(opActualReflectDateTime), 
				opScheReflectDateTime == null ? Optional.empty() : Optional.of(opScheReflectDateTime), 
				opReasonActualCantReflect == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opReasonActualCantReflect, ReasonNotReflectDaily.class)), 
				opReasonScheCantReflect == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opReasonScheCantReflect, ReasonNotReflect.class)));
	}
	
}
