package nts.uk.ctx.at.schedule.app.find.executionlog;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleErrorLogDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.SCEmployeeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmployeeDto;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;

/**
 * The Class ScheduleErrorLogFinder.
 */
@Stateless
public class ScheduleErrorLogFinder {

	/** The schedule error log repository. */
	@Inject
	private ScheduleErrorLogRepository scheduleErrorLogRepository;

	/** The employee adapter. */
	@Inject
	private SCEmployeeAdapter employeeAdapter;

	/**
	 * Find all by exe id.
	 *
	 * @param executionId
	 *            the execution id
	 * @return the list
	 */
	public List<ScheduleErrorLogDto> findAllByExeId(String executionId) {
		List<ScheduleErrorLog> lstError = this.scheduleErrorLogRepository.findByExecutionId(executionId);
		if (lstError == null) {
			return Collections.emptyList();
		}
		List<String> sIds = lstError.stream().map(x-> x.getEmployeeId()).distinct().collect(Collectors.toList());
		List<EmployeeDto> employees = employeeAdapter.findByEmployeeIds(sIds);
		
		return lstError.stream().map(item -> {
			ScheduleErrorLogDto dto = new ScheduleErrorLogDto();
			item.saveToMemento(dto);
			Optional<EmployeeDto> optEmployee =  employees.stream().filter(x -> x.getEmployeeId().equals(dto.getEmployeeId())).findFirst();
			if(!optEmployee.isPresent()){
				return dto;
			}
			EmployeeDto employee =  optEmployee.get();
			dto.setEmployeeCode(employee.getEmployeeCode());
			dto.setEmployeeName(employee.getEmployeeName());
			return dto;
		}).collect(Collectors.toList());
	}
}
