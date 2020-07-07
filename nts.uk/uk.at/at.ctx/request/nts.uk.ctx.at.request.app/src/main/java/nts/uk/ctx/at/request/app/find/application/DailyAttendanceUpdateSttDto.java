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
	private String actualResultDateTime;
	
	/**
	 * 予定反映日時
	 */
	private String scheduleReflectionDateTime;
	
	/**
	 * 実績反映不可理由
	 */
	private Integer opReasonResultsCantReflected;
	
	/**
	 * 予定反映不可理由
	 */
	private Integer opReasonScheduleCantReflected;
	
	public static DailyAttendanceUpdateSttDto fromDomain(DailyAttendanceUpdateStatus dailyAttendanceUpdateStatus) {
		return new DailyAttendanceUpdateSttDto(
				dailyAttendanceUpdateStatus.getActualResultDateTime().map(x -> x.toString()).orElse(null), 
				dailyAttendanceUpdateStatus.getScheduleReflectionDateTime().map(x -> x.toString()).orElse(null), 
				dailyAttendanceUpdateStatus.getOpReasonResultsCantReflected().map(x -> x.value).orElse(null), 
				dailyAttendanceUpdateStatus.getOpReasonScheduleCantReflected().map(x -> x.value).orElse(null));
	}
	
	public DailyAttendanceUpdateStatus toDomain() {
		return new DailyAttendanceUpdateStatus(
				actualResultDateTime == null ? Optional.empty() : Optional.of(GeneralDateTime.fromString(actualResultDateTime, "yyyy/MM/dd HH:mm:ss")), 
				scheduleReflectionDateTime == null ? Optional.empty() : Optional.of(GeneralDateTime.fromString(scheduleReflectionDateTime, "yyyy/MM/dd HH:mm:ss")), 
				opReasonResultsCantReflected == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opReasonResultsCantReflected, ReasonNotReflectDaily.class)), 
				opReasonScheduleCantReflected == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opReasonScheduleCantReflected, ReasonNotReflect.class)));
	}
}
