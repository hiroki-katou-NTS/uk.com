package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 予定実績
 * @author keisuke_hoshina
 *
 */
@AllArgsConstructor
@Getter
public class SchedulePerformance {
	
	/** 1日の計算範囲 */
	private CalculationRangeOfOneDay calculationRangeOfOneDay;
	/** 勤務種類 */
	private Optional<WorkType> workType;
	/** 統合就業時間帯 */
	private Optional<IntegrationOfWorkTime> integrationOfWorkTime;
}
