package nts.uk.screen.at.app.kmk004.m;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.screen.at.app.query.kmk004.p.DailyUnitDto;
import nts.uk.screen.at.app.query.kmk004.p.WeeklyUnitDto;
import nts.uk.screen.at.app.query.kmk004.p.WorkingTimeSettingDto;

/**
 * 職場別変形労働法定労働時間
 * 
 * @author tutt
 *
 */
@Data
public class DeforLaborTimeWkpDto extends WorkingTimeSettingDto {

	/** 職場ID */
	private String wkpId;

	public DeforLaborTimeWkpDto(String wkpId, String comId, WeeklyUnitDto weeklyTime, DailyUnitDto dailyTime) {
		super(comId, weeklyTime, dailyTime);
		this.wkpId = wkpId;
	}

	public static DeforLaborTimeWkpDto fromDomain(DeforLaborTimeWkp domain) {
		return new DeforLaborTimeWkpDto(domain.getWorkplaceId(), domain.getComId(),
				new WeeklyUnitDto(domain.getWeeklyTime().getTime().v()),
				new DailyUnitDto(domain.getDailyTime().getDailyTime().v()));
	}

}
