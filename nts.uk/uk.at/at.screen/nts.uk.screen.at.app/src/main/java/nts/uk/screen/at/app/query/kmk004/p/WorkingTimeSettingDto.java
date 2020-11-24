package nts.uk.screen.at.app.query.kmk004.p;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WorkingTimeSetting;

/**
 * 
 * @author tutt
 *
 */
@Getter
@Setter
public class WorkingTimeSettingDto {

	//週単位
	protected WeeklyUnitDto weeklyTime;

	//日単位
	protected DailyUnitDto dailyTime;

	/**
	 * 
	 * @param domain
	 * @return
	 */
	public static WorkingTimeSettingDto fromDomain(WorkingTimeSetting domain) {
		WorkingTimeSettingDto dto = new WorkingTimeSettingDto();
		/** 本番ならWeeklyUnitDtoのStartを消すべきだが画面が古い版ですので、一旦固定で0を設定する */
		WeeklyUnitDto weeklyTime = new WeeklyUnitDto(domain.getWeeklyTime().getTime().v());
		DailyUnitDto dailyTime = new DailyUnitDto(domain.getDailyTime().getDailyTime().v());
		dto.setWeeklyTime(weeklyTime);
		dto.setDailyTime(dailyTime);
		return dto;
	}
}
