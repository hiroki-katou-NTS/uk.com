package nts.uk.query.app.ccg005.query.work.information.dto;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.1日勤務
 */
@Builder
@Data
public class DailyWorkDto {
	// 勤務区分
	private Integer workTypeUnit;

	// 1日
	private Integer oneDay;

	// 午前
	private Integer morning;

	// 午後
	private Integer afternoon;
	
	public static DailyWorkDto toDto (DailyWork domain) {
		return DailyWorkDto.builder()
				.workTypeUnit(domain.getWorkTypeUnit().value)
				.oneDay(domain.getOneDay().value)
				.morning(domain.getMorning().value)
				.afternoon(domain.getAfternoon().value)
				.build();
	}
}
