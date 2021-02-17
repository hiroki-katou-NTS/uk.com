package nts.uk.screen.at.app.kmk004.o;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.screen.at.app.query.kmk004.p.DailyUnitDto;
import nts.uk.screen.at.app.query.kmk004.p.WeeklyUnitDto;
import nts.uk.screen.at.app.query.kmk004.p.WorkingTimeSettingDto;

/**
 * 社員別変形労働法定労働時間
 * 
 * @author tutt
 *
 */
@Data
public class DeforLaborTimeShaDto extends WorkingTimeSettingDto {

	// 社員ID
	private String empId;

	public DeforLaborTimeShaDto(String empId, String comId, WeeklyUnitDto weeklyTime, DailyUnitDto dailyTime) {
		super(comId, weeklyTime, dailyTime);
		this.empId = empId;
	}

	public static DeforLaborTimeShaDto fromDomain(DeforLaborTimeSha domain) {
		return new DeforLaborTimeShaDto(domain.getEmpId(), domain.getComId(),
				new WeeklyUnitDto(domain.getWeeklyTime().getTime().v()),
				new DailyUnitDto(domain.getDailyTime().getDailyTime().v()));
	}

}
