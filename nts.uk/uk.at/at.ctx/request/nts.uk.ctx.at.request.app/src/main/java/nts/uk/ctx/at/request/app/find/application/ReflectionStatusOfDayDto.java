package nts.uk.ctx.at.request.app.find.application;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ReflectionStatusOfDayDto {
	/**
	 * 実績反映状態
	 */
	private int actualReflectStatus;
	
	/**
	 * 予定反映状態
	 */
	private int scheReflectStatus;
	
	/**
	 * 対象日
	 */
	private String targetDate;
	
	/**
	 * 申請反映の更新状態
	 */
	private DailyAttendanceUpdateSttDto opUpdateStatusAppReflect;
	
	/**
	 * 申請取消の更新状態
	 */
	private DailyAttendanceUpdateSttDto opUpdateStatusAppCancel;
	
	public static ReflectionStatusOfDayDto fromDomain(ReflectionStatusOfDay reflectionStatusOfDay) {
		return new ReflectionStatusOfDayDto(
				reflectionStatusOfDay.getActualReflectStatus().value, 
				reflectionStatusOfDay.getScheReflectStatus().value, 
				reflectionStatusOfDay.getTargetDate().toString(), 
				reflectionStatusOfDay.getOpUpdateStatusAppReflect().map(x -> DailyAttendanceUpdateSttDto.fromDomain(x)).orElse(null), 
				reflectionStatusOfDay.getOpUpdateStatusAppCancel().map(x -> DailyAttendanceUpdateSttDto.fromDomain(x)).orElse(null));
	}
	
	public ReflectionStatusOfDay toDomain() {
		return new ReflectionStatusOfDay(
				EnumAdaptor.valueOf(actualReflectStatus, ReflectedState.class), 
				EnumAdaptor.valueOf(scheReflectStatus, ReflectedState.class), 
				GeneralDate.fromString(targetDate, "yyyy/MM/dd"), 
				opUpdateStatusAppReflect == null ? Optional.empty() : Optional.of(opUpdateStatusAppReflect.toDomain()), 
				opUpdateStatusAppCancel == null ? Optional.empty() : Optional.of(opUpdateStatusAppCancel.toDomain()));
	}
}
