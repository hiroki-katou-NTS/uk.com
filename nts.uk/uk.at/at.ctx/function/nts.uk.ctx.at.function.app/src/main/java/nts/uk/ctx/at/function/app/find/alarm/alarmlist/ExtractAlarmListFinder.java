package nts.uk.ctx.at.function.app.find.alarm.alarmlist;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.ExtractAlarmListService;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.ExtractedAlarmDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.FuncEmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;

@Stateless
public class ExtractAlarmListFinder {

	@Inject
	private ExtractAlarmListService checkOutputAlarmListService;

	public ExtractedAlarmDto checkOutputAlarmList(List<FuncEmployeeSearchDto> listEmployee, String checkPatternCode,
			List<PeriodByAlarmCategory> listOutputScreenA) {
		return this.checkOutputAlarmListService.extractAlarm(listEmployee, checkPatternCode, listOutputScreenA);
	}
}
