package nts.uk.ctx.at.function.ac.alarm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.alarm.BasicScheduleAdapter;
import nts.uk.ctx.at.function.dom.adapter.alarm.BasicScheduleImport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicSchedulePub;

@Stateless
public class BasicSchedulePubAdapterImpl implements BasicScheduleAdapter {

	@Inject
	private ScBasicSchedulePub pub;
	

	@Override
	public List<BasicScheduleImport> getByEmpIdsAndPeriod(List<String> empIds, DatePeriod period) {
		return pub.findById(empIds, period).stream()
				.map(e -> new BasicScheduleImport(e.getEmployeeId()
						, e.getDate()
						, e.getWorkTypeCode()
						, e.getWorkTimeCode()))
				.collect(Collectors.toList());
	}


	@Override
	public Optional<GeneralDate> acquireMaxDateBasicSchedule(List<String> empIds) {
		Optional<GeneralDate> data = pub.acquireMaxDateBasicSchedule(empIds);
		return data;
	}
	
}
