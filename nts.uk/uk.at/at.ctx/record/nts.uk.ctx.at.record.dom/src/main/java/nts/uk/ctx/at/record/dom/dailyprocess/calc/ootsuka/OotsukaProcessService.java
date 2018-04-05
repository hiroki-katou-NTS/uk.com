package nts.uk.ctx.at.record.dom.dailyprocess.calc.ootsuka;

import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 日別計算の大塚専用処理
 * @author keisuke_hoshina
 *
 */
public interface OotsukaProcessService {

	public WorkType getOotsukaWorkType(WorkType workType,
			   						   /*就業時間帯*/
			   						   TimeLeavingOfDailyPerformance attendanceLeaving);
}
