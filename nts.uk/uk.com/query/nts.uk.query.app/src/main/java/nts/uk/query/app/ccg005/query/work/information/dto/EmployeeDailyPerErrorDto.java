package nts.uk.query.app.ccg005.query.work.information.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.勤務情報の取得.日別実績エラー一覧
 */

@Builder
@Data
public class EmployeeDailyPerErrorDto {

	// 社員ID
	private String sid;

	// 年月日
	private GeneralDate ymd;
	
	//エラーアラームコード
	private String errorAlarmWorkRecordCode;

	public static EmployeeDailyPerErrorDto getDto(EmployeeDailyPerError domain) {
		if (domain == null) {
			return EmployeeDailyPerErrorDto.builder().build();
		}
		return EmployeeDailyPerErrorDto.builder()
				.sid(domain.getEmployeeID())
				.ymd(domain.getDate())
				.errorAlarmWorkRecordCode(domain.getErrorAlarmWorkRecordCode().v())
				.build();
	}
}
