package nts.uk.ctx.at.shared.dom.application.common;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDateTime;

/**
 * 日別勤怠の更新状態
 * @author thanh_nx
 *
 */
@Getter
public class DailyAttendanceUpdateStatusShare {
	
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
	private Optional<ReasonNotReflectDailyShare> opReasonActualCantReflect;
	
	/**
	 * 予定反映不可理由
	 */
	private Optional<ReasonNotReflectShare> opReasonScheCantReflect;
	
	public DailyAttendanceUpdateStatusShare(Optional<GeneralDateTime> opActualReflectDateTime, Optional<GeneralDateTime> opScheReflectDateTime,
			Optional<ReasonNotReflectDailyShare> opReasonActualCantReflect, Optional<ReasonNotReflectShare> opReasonScheCantReflect) {
		this.opActualReflectDateTime = opActualReflectDateTime;
		this.opScheReflectDateTime = opScheReflectDateTime;
		this.opReasonActualCantReflect = opReasonActualCantReflect;
		this.opReasonScheCantReflect = opReasonScheCantReflect;
	}
	
	public static DailyAttendanceUpdateStatusShare createNew(GeneralDateTime opActualReflectDateTime, GeneralDateTime opScheReflectDateTime,
			Integer opReasonActualCantReflect, Integer opReasonScheCantReflect) {
		return new DailyAttendanceUpdateStatusShare(
				opActualReflectDateTime == null ? Optional.empty() : Optional.of(opActualReflectDateTime), 
				opScheReflectDateTime == null ? Optional.empty() : Optional.of(opScheReflectDateTime), 
				opReasonActualCantReflect == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opReasonActualCantReflect, ReasonNotReflectDailyShare.class)), 
				opReasonScheCantReflect == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opReasonScheCantReflect, ReasonNotReflectShare.class)));
	}
	
}
