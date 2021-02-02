package nts.uk.screen.at.app.command.kmk.kmk004;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeCom;

/**
 * 
 * @author sonnlb
 *
 *         会社別通常勤務法定労働時間を更新する
 */
@Value
public class UpdateRegularLaborTimeComCommand {

	/** 会社ID */
	private String comId;

	/** 週単位 */
	private int weeklyTime;

	/** 日単位 */
	private int dailyTime;

	public RegularLaborTimeCom toDomain() {

		return RegularLaborTimeCom.of(comId, new WeeklyUnit(new WeeklyTime(weeklyTime)),
				new DailyUnit(new TimeOfDay(dailyTime)));
	}

}
