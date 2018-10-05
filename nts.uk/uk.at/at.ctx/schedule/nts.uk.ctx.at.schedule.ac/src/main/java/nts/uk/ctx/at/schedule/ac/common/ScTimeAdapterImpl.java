package nts.uk.ctx.at.schedule.ac.common;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.dailyprocess.scheduletime.ScheduleTimePub;
import nts.uk.ctx.at.record.pub.dailyprocess.scheduletime.ScheduleTimePubExport;
import nts.uk.ctx.at.record.pub.dailyprocess.scheduletime.ScheduleTimePubImport;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeImport;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeParam;

@Stateless
public class ScTimeAdapterImpl implements ScTimeAdapter {
	@Inject
	private ScheduleTimePub scheduleTimePub;
	
	@Override
	public ScTimeImport calculation(Object companySetting, ScTimeParam param) {
		ScheduleTimePubImport impTime = new ScheduleTimePubImport(param.getEmployeeId(), param.getTargetDate(),
				param.getWorkTypeCode(), param.getWorkTimeCode(), param.getStartClock(), param.getEndClock(),
				param.getBreakStartTime(), param.getBreakEndTime(), param.getChildCareStartTime(),
				param.getChildCareEndTime());
		ScheduleTimePubExport export = this.scheduleTimePub.calculationScheduleTime(companySetting, impTime);
		
		return ScTimeImport.builder()
				.actualWorkTime(export.getActualWorkTime())
				.breakTime(export.getBreakTime())
				.childTime(export.getChildTime())
				.careTime(export.getCareTime())
				.flexTime(export.getFlexTime())
				.employeeid(export.getEmployeeid())
				.personalExpenceTime(export.getPersonalExpenceTime())
				.preTime(export.getPreTime())
				.totalWorkTime(export.getTotalWorkTime())
				.weekDayTime(export.getWeekDayTime())
				.ymd(export.getYmd())
				.personalExpenceTime(export.getPersonalExpenceTime())
				.build();
	}

	@Override
	public Object getCompanySettingForCalculation() {
		return this.scheduleTimePub.getCompanySettingForCalclationScheduleTimeForMultiPeople();
	}

}
