/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.executionlog;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.executionlog.dto.PeriodObject;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleExecutionLogDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.EmployeeDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.SCEmployeeAdapter;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Period;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ScheduleExecutionLogFinder.
 */
@Stateless
public class ScheduleExecutionLogFinder {

	/** The schedule execution log repository. */
	@Inject
	private ScheduleExecutionLogRepository scheduleExecutionLogRepository;

	/** The employee adapter. */
	@Inject
	private SCEmployeeAdapter employeeAdapter;
	
	public List<ScheduleExecutionLogDto> findByDate(PeriodObject periodObj) {
		String companyId = AppContexts.user().companyId();
		if (periodObj == null) {
			return null;
		}
		Period period = new Period(periodObj.getStartDate(), periodObj.getEndDate());
		List<ScheduleExecutionLog> sel = scheduleExecutionLogRepository.find(companyId, period);
		if (sel == null) {
			return null;
		}
		return sel.stream().map(item -> {
			ScheduleExecutionLogDto dto = new ScheduleExecutionLogDto();
			item.saveToMemento(dto);
			EmployeeDto employee =employeeAdapter.findByEmployeeId(dto.getExecutionEmployeeId());
			 dto.setEmployeeCode(employee.getEmployeeCode());
			 dto.setEmployeeName(employee.getEmployeeName());
			return dto;
		}).collect(Collectors.toList());
	}
	
	/**
	 * Find by id.
	 *
	 * @param executionId the execution id
	 * @return the schedule execution log dto
	 */
	public ScheduleExecutionLogDto findById(String executionId) {
		
		// get company id
		String companyId = AppContexts.user().companyId();
		
		ScheduleExecutionLogDto dto = new ScheduleExecutionLogDto();
		 
		// call repository find by id
		Optional<ScheduleExecutionLog> optionalScheduleExecutionLog = this.scheduleExecutionLogRepository
				.findById(companyId, executionId);
		
		// check exist data
		if(optionalScheduleExecutionLog.isPresent()){
			optionalScheduleExecutionLog.get().saveToMemento(dto);
		}
		return dto;
	}
}
