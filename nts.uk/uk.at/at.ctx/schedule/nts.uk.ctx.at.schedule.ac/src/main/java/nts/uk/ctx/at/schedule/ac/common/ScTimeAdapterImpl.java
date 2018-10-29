package nts.uk.ctx.at.schedule.ac.common;

import java.util.List;
import java.util.stream.Collectors;

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
		
		ScheduleTimePubExport export = this.scheduleTimePub.calculationScheduleTime(
				companySetting,
				createScheduleTimePubImport(param));
		return createScTimeImport(export);
	}
	
	@Override
	public List<ScTimeImport> calculation(Object companySetting, List<ScTimeParam> params) {
		
		return this.scheduleTimePub.calclationScheduleTimeForMultiPeople(
				companySetting,
				params.stream().map(p -> createScheduleTimePubImport(p)).collect(Collectors.toList()))
				.stream()
				.map(o -> createScTimeImport(o))
				.collect(Collectors.toList());
	}

	@Override
	public Object getCompanySettingForCalculation() {
		return this.scheduleTimePub.getCompanySettingForCalclationScheduleTimeForMultiPeople();
	}

	private static ScheduleTimePubImport createScheduleTimePubImport(ScTimeParam param) {
		ScheduleTimePubImport impTime = new ScheduleTimePubImport(param.getEmployeeId(), param.getTargetDate(),
				param.getWorkTypeCode(), param.getWorkTimeCode(), param.getStartClock(), param.getEndClock(),
				param.getBreakStartTime(), param.getBreakEndTime(), param.getChildCareStartTime(),
				param.getChildCareEndTime());
		return impTime;
	}

	private static ScTimeImport createScTimeImport(ScheduleTimePubExport export) {
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

}
