package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.InfoWithDateApplication;

@AllArgsConstructor
@NoArgsConstructor
public class InfoWithDateApplicationCommand {
	// 休憩時間帯設定
	public BreakTimeZoneSettingCommand breakTime;
	// 勤務時間
	public WorkHoursCommand workHours;
	// 残業指示
	
	// 申請時間
	public ApplicationTimeCommand applicationTime;
	// 初期の勤務種類コード
	public String workTypeCD;
	// 初期の就業時間帯コード
	public String workTimeCD;
	
	
	public InfoWithDateApplication toDomain() {
		return new InfoWithDateApplication(
				breakTime == null ? Optional.empty() : Optional.of(breakTime.toDomain()),
				workHours == null ? Optional.empty() : Optional.of(workHours.toDomain()),
				applicationTime == null ? Optional.empty() : Optional.of(applicationTime.toDomain()),
				Optional.ofNullable(workTypeCD),
				Optional.ofNullable(workTimeCD));
	}
}
