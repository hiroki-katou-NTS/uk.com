package nts.uk.ctx.office.dom.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.勤務情報の取得.日別実績勤務
 */

@Builder
@Data
public class TimeLeavingOfDailyPerformanceDto {
	
	//年月日
	private GeneralDate ymd;
	
	//社員ID
	private String sid;
	
	// 出勤時刻
	private Integer attendanceTime;

	// 退勤時刻
	private Integer leaveTime;
}
