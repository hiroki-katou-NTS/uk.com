package nts.uk.ctx.at.schedule.app.find.executionlog;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.executionlog.dto.PeriodObject;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleExecutionLogDto;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Period;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ScheduleExecutionLogFinder {

	@Inject
	private ScheduleExecutionLogRepository scheduleExecutionLogRepository;

	public List<ScheduleExecutionLogDto> findByDate(PeriodObject periodObj) {
		String companyId = AppContexts.user().companyId();
		if (periodObj == null) {
			return null;
		}
		Period period = new Period(periodObj.getStartDate(), periodObj.getEndDate());
		List<ScheduleExecutionLog> sel = scheduleExecutionLogRepository.find(companyId, period);
		return sel.stream().map(item -> {
			ScheduleExecutionLogDto dto = new ScheduleExecutionLogDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
