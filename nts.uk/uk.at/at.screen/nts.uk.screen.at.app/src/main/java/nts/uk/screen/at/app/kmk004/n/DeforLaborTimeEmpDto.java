package nts.uk.screen.at.app.kmk004.n;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.screen.at.app.query.kmk004.p.DailyUnitDto;
import nts.uk.screen.at.app.query.kmk004.p.WeeklyUnitDto;
import nts.uk.screen.at.app.query.kmk004.p.WorkingTimeSettingDto;

/**
 * 雇用別変形労働法定労働時間
 * 
 * @author tutt
 *
 */
@Data
public class DeforLaborTimeEmpDto extends WorkingTimeSettingDto {

	/** 雇用コード */
	private String empCd;

	public DeforLaborTimeEmpDto(String empCd, String comId, WeeklyUnitDto weeklyTime, DailyUnitDto dailyTime) {
		super(comId, weeklyTime, dailyTime);
		this.empCd = empCd;
	}

	public static DeforLaborTimeEmpDto fromDomain(DeforLaborTimeEmp domain) {
		return new DeforLaborTimeEmpDto(domain.getEmploymentCode().v(), domain.getComId(),
				new WeeklyUnitDto(domain.getWeeklyTime().getTime().v()),
				new DailyUnitDto(domain.getDailyTime().getDailyTime().v()));

	}

}
