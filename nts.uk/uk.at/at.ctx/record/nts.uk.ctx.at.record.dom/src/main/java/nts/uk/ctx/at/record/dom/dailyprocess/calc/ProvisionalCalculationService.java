package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.shorttimework.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 日別実績の仮計算(申請・スケからの窓口)
 * @author keisuke_hoshina
 *
 */
public interface ProvisionalCalculationService {
	
	public Optional<IntegrationOfDaily> calculation(String employeeId,GeneralDate targetDate,Map<Integer, TimeZone> timeSheets,
										  WorkTypeCode workTypeCode, WorkTimeCode workTimeCode,
										  List<BreakTimeSheet> breakTimeSheets,
										  List<OutingTimeSheet> outingTimeSheets,
										  List<ShortWorkingTimeSheet> shortWorkingTimeSheets); 
}
