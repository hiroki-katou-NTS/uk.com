package nts.uk.ctx.at.request.app.find.application;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.DailyAttendanceUpdateStatus;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflectDaily;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class DailyAttendanceUpdateSttDto {
	/**
	 * 実績反映日時
	 */
	private String opActualReflectDateTime;
	
	/**
	 * 予定反映日時
	 */
	private String opScheReflectDateTime;
	
	/**
	 * 実績反映不可理由
	 */
	private Integer opReasonActualCantReflect;
	
	/**
	 * 予定反映不可理由
	 */
	private Integer opReasonScheCantReflect;
	
	public static DailyAttendanceUpdateSttDto fromDomain(DailyAttendanceUpdateStatus dailyAttendanceUpdateStatus) {
		return new DailyAttendanceUpdateSttDto(
				dailyAttendanceUpdateStatus.getOpActualReflectDateTime().map(x -> x.toString()).orElse(null), 
				dailyAttendanceUpdateStatus.getOpScheReflectDateTime().map(x -> x.toString()).orElse(null), 
				dailyAttendanceUpdateStatus.getOpReasonActualCantReflect().map(x -> x.value).orElse(null), 
				dailyAttendanceUpdateStatus.getOpReasonScheCantReflect().map(x -> x.value).orElse(null));
	}
	
	public DailyAttendanceUpdateStatus toDomain() {
		return new DailyAttendanceUpdateStatus(
				opActualReflectDateTime == null ? Optional.empty() : Optional.of(GeneralDateTime.fromString(opActualReflectDateTime, "yyyy/MM/dd HH:mm:ss")), 
				opScheReflectDateTime == null ? Optional.empty() : Optional.of(GeneralDateTime.fromString(opScheReflectDateTime, "yyyy/MM/dd HH:mm:ss")), 
				opReasonActualCantReflect == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opReasonActualCantReflect, ReasonNotReflectDaily.class)), 
				opReasonScheCantReflect == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opReasonScheCantReflect, ReasonNotReflect.class)));
	}
}
