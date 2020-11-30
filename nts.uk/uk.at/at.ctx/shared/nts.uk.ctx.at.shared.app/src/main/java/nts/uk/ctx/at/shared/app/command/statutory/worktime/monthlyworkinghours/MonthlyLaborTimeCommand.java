package nts.uk.ctx.at.shared.app.command.statutory.worktime.monthlyworkinghours;

import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyLaborTime;

/**
 * 
 * @author sonnlb
 *
 */
@Value
public class MonthlyLaborTimeCommand {
	/** 法定労働時間 */
	private int legalLaborTime;

	/** 所定労働時間 */
	/** 勤務区分がフレックスの場合、必ず所定労働時間と週平均時間が存在する */
	private Integer withinLaborTime;

	/** 週平均時間 */
	/** 勤務区分がフレックスの場合、必ず所定労働時間と週平均時間が存在する */
	private Integer weekAvgTime;

	public MonthlyLaborTime toDomain() {
		return MonthlyLaborTime.of(new MonthlyEstimateTime(this.legalLaborTime),
				Optional.ofNullable(
						this.withinLaborTime == null ? null : new MonthlyEstimateTime(this.withinLaborTime)),
				Optional.ofNullable(this.weekAvgTime == null ? null : new MonthlyEstimateTime(this.weekAvgTime)));
	}
}
