package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week;

import java.io.Serializable;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 労働時間設定
 */
@Getter
public abstract class WorkingTimeSetting extends AggregateRoot implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	private String comId;
	
	/** 週単位 */
	private WeeklyUnit weeklyTime;

	/** 日単位 */
	private DailyUnit dailyTime;

	protected WorkingTimeSetting(String comId, WeeklyUnit weeklyTime, DailyUnit dailyTime) {
		this.comId = comId;
		this.weeklyTime = weeklyTime;
		this.dailyTime = dailyTime;
	}
}
