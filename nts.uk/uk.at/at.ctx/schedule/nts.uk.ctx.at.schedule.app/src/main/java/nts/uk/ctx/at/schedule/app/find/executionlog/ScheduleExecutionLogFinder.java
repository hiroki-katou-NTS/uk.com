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

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.PeriodObject;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleExecutionLogDto;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleExecutionLogInfoDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.SCEmployeeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmployeeDto;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ScheduleExecutionLogFinder.
 */
@Stateless
public class ScheduleExecutionLogFinder {

	/** The schedule execution log repository. */
	@Inject
	private ScheduleExecutionLogRepository scheduleExecutionLogRepository;
	
	/** The schedule creator repository. */
	@Inject
	private ScheduleCreatorRepository scheduleCreatorRepository;
	/** The schedule creator repository. */
	@Inject
	private ScheduleErrorLogRepository scheduleErrorLogRepository;
	
	/** The Constant DEFAULT_NUMBER. */
	public static final int DEFAULT_NUMBER = 0;
	
	/** The Constant NEXT_NUMBER. */
	public static final int NEXT_NUMBER = 1;

	/** The employee adapter. */
	@Inject
	private SCEmployeeAdapter employeeAdapter;
	
	/**
	 * Find by date.
	 *
	 * @param period the period
	 * @return the list
	 */
	public List<ScheduleExecutionLogDto> findByDate(PeriodObject period) {
		String companyId = AppContexts.user().companyId();
		if (period == null) {
			return null;
		}
		List<ScheduleExecutionLog> sel = scheduleExecutionLogRepository.find(companyId,
				period.getStartDate(), period.getEndDate());
		if (sel == null) {
			return null;
		}
		return sel.stream().map(item -> {
			ScheduleExecutionLogDto dto = new ScheduleExecutionLogDto();
			item.saveToMemento(dto);
			EmployeeDto employee = employeeAdapter.findByEmployeeId(dto.getExecutionEmployeeId());
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
	
	/**
	 * Find info by id.
	 *
	 * @param executionId the execution id
	 * @return the schedule execution log info dto
	 */
	public ScheduleExecutionLogInfoDto findInfoById(String executionId){
		List<ScheduleCreator> scheduleCreators =  this.scheduleCreatorRepository.findAll(executionId);
		List<ScheduleErrorLog> scheduleErrorLogs = this.scheduleErrorLogRepository
				.findByExecutionId(executionId);
		ScheduleExecutionLogInfoDto dto = new ScheduleExecutionLogInfoDto();
		if (!CollectionUtil.isEmpty(scheduleCreators)) {
			dto.setTotalNumber(scheduleCreators.size());
		} 
		if (!CollectionUtil.isEmpty(scheduleErrorLogs)) {
			dto.setTotalNumberError(scheduleErrorLogs.size());
		}
		if (!CollectionUtil.isEmpty(scheduleCreators)) {
			dto.setTotalNumberCreated(this.scheduleCreatorRepository.countByStatus(executionId,
					ExecutionStatus.CREATED.value));
		}
		
		return dto;
	}
}
