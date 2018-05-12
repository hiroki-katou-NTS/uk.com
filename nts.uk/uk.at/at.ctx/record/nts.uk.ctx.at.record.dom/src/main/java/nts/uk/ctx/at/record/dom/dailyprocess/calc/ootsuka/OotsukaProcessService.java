package nts.uk.ctx.at.record.dom.dailyprocess.calc.ootsuka;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculation;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 日別計算の大塚専用処理
 * @author keisuke_hoshina
 *
 */
public interface OotsukaProcessService {

	public WorkType getOotsukaWorkType(WorkType workType,Optional<FixedWorkCalcSetting> calcMethodOfFixWork,TimeLeavingOfDailyPerformance attendanceLeaving, HolidayCalculation holidayCalculation);
	
	public boolean decisionOotsukaMode(WorkType workType,Optional<FixedWorkCalcSetting> calcMethodOfFixWork,TimeLeavingOfDailyPerformance attendanceLeaving, HolidayCalculation holidayCalculation);
}
