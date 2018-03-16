package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;

@Getter
@Setter
public class WeeklyTimeNewDto {
	
	/** The weekly time. */
	/* 時間. */
	private WeeklyTime weeklyTime;

	/** The week start. */
	/* 週開始. */
	private WeekStart weekStart;
	


}
