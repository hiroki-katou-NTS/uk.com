package nts.uk.ctx.office.dom.dto;

import lombok.Builder;
import lombok.Data;

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
}
