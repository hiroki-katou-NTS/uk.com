package nts.uk.query.pub.ccg005.work.information.dto;

import lombok.Builder;
import lombok.Data;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.日別勤務種類
 */
@Data
@Builder
public class WorkTypeExport {

	//1日の勤務
	private DailyWorkExport dailyWork;
	
	//コード
	private String code;
	
	//表示名
	private String displayName;
}
