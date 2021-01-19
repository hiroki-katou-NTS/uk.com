package nts.uk.ctx.office.dom.dto;

import lombok.Builder;
import lombok.Data;

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
}
