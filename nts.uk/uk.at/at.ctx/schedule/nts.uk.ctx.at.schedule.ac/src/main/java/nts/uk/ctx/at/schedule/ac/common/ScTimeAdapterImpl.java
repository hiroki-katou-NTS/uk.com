package nts.uk.ctx.at.schedule.ac.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.dailyprocess.scheduletime.ScheduleTimePub;
import nts.uk.ctx.at.record.pub.dailyprocess.scheduletime.ScheduleTimePubExport;
import nts.uk.ctx.at.record.pub.dailyprocess.scheduletime.ScheduleTimePubImport;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeImport;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeParam;

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
public class ScTimeAdapterImpl implements ScTimeAdapter {
	@Inject
	private ScheduleTimePub scheduleTimePub;

	@Override
	public List<ScTimeImport> calculation(List<ScTimeParam> param) {
		List<ScheduleTimePubImport> listScheduleTimePubImport = new ArrayList<>();
		param.forEach(x -> {
			listScheduleTimePubImport.add(new ScheduleTimePubImport(x.getEmployeeId(), x.getTargetDate(),
					x.getWorkTypeCode(), x.getWorkTimeCode(), x.getStartClock(), x.getEndClock(), x.getBreakStartTime(),
					x.getBreakEndTime(), x.getChildCareStartTime(), x.getChildCareEndTime()));
		});

		List<ScheduleTimePubExport> listExport = this.scheduleTimePub
				.calclationScheduleTimeForMultiPeople(listScheduleTimePubImport);

		return listExport.stream().map(x -> {
			return ScTimeImport.builder().actualWorkTime(x.getActualWorkTime()).breakTime(x.getBreakTime())
					.childCareTime(x.getChildCareTime()).employeeid(x.getEmployeeid())
					.personalExpenceTime(x.getPersonalExpenceTime()).preTime(x.getPreTime())
					.totalWorkTime(x.getTotalWorkTime()).weekDayTime(x.getWeekDayTime()).ymd(x.getYmd()).build();
		}).collect(Collectors.toList());
	}

}
