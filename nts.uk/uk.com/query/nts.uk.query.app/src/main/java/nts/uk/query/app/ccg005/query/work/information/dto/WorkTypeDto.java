package nts.uk.query.app.ccg005.query.work.information.dto;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.日別勤務種類
 */
@Data
@Builder
public class WorkTypeDto {

	//1日の勤務
	private DailyWorkDto dailyWork;
	
	//コード
	private String code;
	
	//表示名
	private String displayName;
	
	public static WorkTypeDto toDto (WorkType domain) {
		if (domain == null) {
			return WorkTypeDto.builder().build();
		}
		return WorkTypeDto.builder()
				.dailyWork(DailyWorkDto.toDto(domain.getDailyWork()))
				.code(domain.getWorkTypeCode().v())
				.displayName(domain.getName().v())
				.build();
	}
}
