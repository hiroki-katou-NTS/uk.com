package nts.uk.ctx.at.schedule.app.find.executionlog;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleCreatorDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.SCEmployeeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmployeeDto;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository;

/**
 * The Class ScheduleCreatorFinder.
 */
@Stateless
public class ScheduleCreatorFinder {
	
	/** The schedule creator repository. */
	@Inject
	private ScheduleCreatorRepository scheduleCreatorRepository;
	
	/** The employee adapter. */
	@Inject
	private SCEmployeeAdapter employeeAdapter;
	
	/**
	 * Find all by exe id.
	 *
	 * @param executionId the execution id
	 * @return the list
	 */
	public List<ScheduleCreatorDto> findAllByExeId(String executionId) {
		
		List<ScheduleCreator> lstCreator = this.scheduleCreatorRepository.findAll(executionId);
		return lstCreator.stream().map(item -> {
			ScheduleCreatorDto dto = new ScheduleCreatorDto();
			item.saveToMemento(dto);
			EmployeeDto employee = employeeAdapter.findByEmployeeId(dto.getEmployeeId());
			dto.setEmployeeCode(employee.getEmployeeCode());
			dto.setEmployeeName(employee.getEmployeeName());
			return dto;
		}).collect(Collectors.toList());
	}
}
