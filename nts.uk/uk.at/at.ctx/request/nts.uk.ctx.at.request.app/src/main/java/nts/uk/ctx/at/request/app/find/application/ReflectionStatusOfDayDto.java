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
	private int resultsReflectedStatus;
	
	/**
	 * 予定反映状態
	 */
	private int scheduleReflectionStatus;
	
	/**
	 * 対象日
	 */
	private String targetDate;
	
	/**
	 * 申請反映の更新状態
	 */
	private DailyAttendanceUpdateSttDto opUpdateStatusAppReflection;
	
	/**
	 * 申請取消の更新状態
	 */
	private DailyAttendanceUpdateSttDto opUpdateStatusAppCancellation;
	
	public static ReflectionStatusOfDayDto fromDomain(ReflectionStatusOfDay reflectionStatusOfDay) {
		return new ReflectionStatusOfDayDto(
				reflectionStatusOfDay.getResultsReflectedStatus().value, 
				reflectionStatusOfDay.getScheduleReflectionStatus().value, 
				reflectionStatusOfDay.getTargetDate().toString(), 
				reflectionStatusOfDay.getOpUpdateStatusAppReflection().map(x -> DailyAttendanceUpdateSttDto.fromDomain(x)).orElse(null), 
				reflectionStatusOfDay.getOpUpdateStatusAppCancellation().map(x -> DailyAttendanceUpdateSttDto.fromDomain(x)).orElse(null));
	}
	
	public ReflectionStatusOfDay toDomain() {
		return new ReflectionStatusOfDay(
				EnumAdaptor.valueOf(resultsReflectedStatus, ReflectedState.class), 
				EnumAdaptor.valueOf(scheduleReflectionStatus, ReflectedState.class), 
				GeneralDate.fromString(targetDate, "yyyy/MM/dd"), 
				opUpdateStatusAppReflection == null ? Optional.empty() : Optional.of(opUpdateStatusAppReflection.toDomain()), 
				opUpdateStatusAppCancellation == null ? Optional.empty() : Optional.of(opUpdateStatusAppCancellation.toDomain()));
	}
}
