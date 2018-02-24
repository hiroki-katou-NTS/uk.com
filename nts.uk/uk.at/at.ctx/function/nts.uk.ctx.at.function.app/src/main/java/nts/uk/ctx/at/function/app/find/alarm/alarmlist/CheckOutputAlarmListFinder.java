package nts.uk.ctx.at.function.app.find.alarm.alarmlist;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.AlarmExtraValueWkReDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.CheckOutputAlarmList;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.OutputScreenA;

@Stateless
public class CheckOutputAlarmListFinder {

	@Inject
	private CheckOutputAlarmList checkOutputAlarmList;

	public List<AlarmExtraValueWkReDto> checkOutputAlarmList(List<String> listEmployee, String checkPatternCode,
			List<OutputScreenA> listOutputScreenA) {
		return this.checkOutputAlarmList.checkOutputAlarmList(listEmployee, checkPatternCode, listOutputScreenA);
	}
}
