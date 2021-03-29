package nts.uk.ctx.at.request.app.find.application.overtime;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.InfoWithDateApplication;

@AllArgsConstructor
@NoArgsConstructor
public class InfoWithDateApplicationDto {
	// 休憩時間帯設定
	public BreakTimeZoneSettingDto breakTime;
	// 勤務時間
	public WorkHoursDto workHours;
	// 残業指示
	
	// 申請時間
	public ApplicationTimeDto applicationTime;
	// 初期の勤務種類コード
	public String workTypeCD;
	// 初期の就業時間帯コード
	public String workTimeCD;
	
	public static InfoWithDateApplicationDto fromDomain(InfoWithDateApplication infoWithDateApplication) {
		if (infoWithDateApplication == null) return null;
		return new InfoWithDateApplicationDto(
				BreakTimeZoneSettingDto.fromDomain(infoWithDateApplication.getBreakTime().orElse(null)),
				WorkHoursDto.fromDomain(infoWithDateApplication.getWorkHours().orElse(null)),
				ApplicationTimeDto.fromDomain(infoWithDateApplication.getApplicationTime().orElse(null)),
				infoWithDateApplication.getWorkTypeCD().orElse(null),
				infoWithDateApplication.getWorkTimeCD().orElse(null));
	}
}
