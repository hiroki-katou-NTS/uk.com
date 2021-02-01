package nts.uk.screen.at.app.query.kmk004.p;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WorkingTimeSetting;

/**
 * 
 * @author tutt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkingTimeSettingDto {
	
	//会社ID
	public String comId;

	//週単位
	public WeeklyUnitDto weeklyTime;

	//日単位
	public DailyUnitDto dailyTime;

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
		dto.setComId(domain.getComId());
		dto.setWeeklyTime(weeklyTime);
		dto.setDailyTime(dailyTime);
		return dto;
	}
}
