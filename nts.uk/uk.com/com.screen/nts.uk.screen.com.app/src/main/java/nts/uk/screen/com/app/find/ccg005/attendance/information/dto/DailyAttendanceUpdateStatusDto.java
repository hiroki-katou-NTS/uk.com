package nts.uk.screen.com.app.find.ccg005.attendance.information.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.DailyAttendanceUpdateStatus;

@Builder
@Data
public class DailyAttendanceUpdateStatusDto {
	/**
	 * 実績反映日時
	 */
	private GeneralDateTime opActualReflectDateTime;

	/**
	 * 予定反映日時
	 */
	private GeneralDateTime opScheReflectDateTime;

	/**
	 * 実績反映不可理由
	 */
	private Integer opReasonActualCantReflect;

	/**
	 * 予定反映不可理由
	 */
	private Integer opReasonScheCantReflect;

	public static DailyAttendanceUpdateStatusDto toDto(DailyAttendanceUpdateStatus domain) {
		return DailyAttendanceUpdateStatusDto.builder()
				.opActualReflectDateTime(domain.getOpActualReflectDateTime().map(v -> v).orElse(null))
				.opScheReflectDateTime(domain.getOpScheReflectDateTime().map(v -> v).orElse(null))
				.opReasonActualCantReflect(domain.getOpReasonActualCantReflect().map(v -> v.value).orElse(null))
				.opReasonScheCantReflect(domain.getOpReasonScheCantReflect().map(v -> v.value).orElse(null)).build();
	}
}
