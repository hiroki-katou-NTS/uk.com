package nts.uk.screen.at.app.kmk004.l;

import lombok.AllArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeCom;
import nts.uk.screen.at.app.query.kmk004.p.DailyUnitDto;
import nts.uk.screen.at.app.query.kmk004.p.WeeklyUnitDto;
import nts.uk.screen.at.app.query.kmk004.p.WorkingTimeSettingDto;

/**
 * 会社別変形労働法定労働時間
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@Setter
public class DeforLaborTimeComDto extends WorkingTimeSettingDto {

	public DeforLaborTimeComDto(String comId, WeeklyUnitDto weeklyTime, DailyUnitDto dailyTime) {
		super(comId, weeklyTime, dailyTime);
	}
	
	public static DeforLaborTimeComDto fromDomain(DeforLaborTimeCom domain) {

		return new DeforLaborTimeComDto(domain.getComId(), new WeeklyUnitDto(domain.getWeeklyTime().getTime().v()),
				new DailyUnitDto(domain.getDailyTime().getDailyTime().v()));
	}
}
