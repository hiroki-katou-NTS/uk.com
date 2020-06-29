package nts.uk.screen.at.app.schedule.personal.correction.shiftdisplay;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.screen.at.app.schedule.personal.correction.shiftdisplay.GetWorkScheduleQuery.WorkScheduleDto;

/**
 * @author anhdt
 *	勤務実績（シフト）を取得する
 */
@Stateless
public class GetWorkRecordQuery {
	
	public WorkScheduleDto get(List<ShiftMaster> shiftMasters, List<String> empIds, DatePeriod period) {
		return null;
	} 
	
	
}
