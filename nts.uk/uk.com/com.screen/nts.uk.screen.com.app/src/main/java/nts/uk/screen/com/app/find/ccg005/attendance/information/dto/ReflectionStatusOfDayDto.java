package nts.uk.screen.com.app.find.ccg005.attendance.information.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;

@Builder
@Data
public class ReflectionStatusOfDayDto {
	/**
	 * 実績反映状態
	 */
	private Integer actualReflectStatus;
	
	/**
	 * 予定反映状態
	 */
	private Integer scheReflectStatus;
	
	/**
	 * 対象日
	 */
	private GeneralDate targetDate;
	
	/**
	 * 申請反映の更新状態
	 */
	private DailyAttendanceUpdateStatusDto opUpdateStatusAppReflect;
	
	/**
	 * 申請取消の更新状態
	 */
	private DailyAttendanceUpdateStatusDto opUpdateStatusAppCancel;
	
	public static ReflectionStatusOfDayDto toDto (ReflectionStatusOfDay domain) {
		return ReflectionStatusOfDayDto.builder()
				.actualReflectStatus(domain.getActualReflectStatus().value)
				.scheReflectStatus(domain.getScheReflectStatus().value)
				.targetDate(domain.getTargetDate())
				.opUpdateStatusAppReflect(domain.getOpUpdateStatusAppReflect()
						.map(v -> DailyAttendanceUpdateStatusDto.toDto(v))
						.orElse(DailyAttendanceUpdateStatusDto.builder().build()))
				.opUpdateStatusAppCancel(domain.getOpUpdateStatusAppCancel()
						.map(v -> DailyAttendanceUpdateStatusDto.toDto(v))
						.orElse(DailyAttendanceUpdateStatusDto.builder().build()))
				.build();
	}
}
