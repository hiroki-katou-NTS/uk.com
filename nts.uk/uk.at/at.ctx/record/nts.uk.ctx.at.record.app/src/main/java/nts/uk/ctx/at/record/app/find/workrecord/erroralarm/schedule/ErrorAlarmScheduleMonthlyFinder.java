package nts.uk.ctx.at.record.app.find.workrecord.erroralarm.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedCheckSDailyItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtracSDailyItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonItemsRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ErrorAlarmScheduleMonthlyFinder {
	@Inject
	private FixedExtractionSMonItemsRepository scheduleFixItemRepository;
	
	/**
	 * Get schedule fix item month
	 * @return list of schedule fix item month
	 */
	public List<ScheFixItemMonthlyDto> getScheFixItem() {		
		return scheduleFixItemRepository.getAll().stream()
				.map(eral -> ScheFixItemMonthlyDto.fromDomain(eral))
				.collect(Collectors.toList());
	}
}
