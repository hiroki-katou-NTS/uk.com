package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTargetRepository;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class AggrPeriodTargetFinder {

	@Inject
	private AggrPeriodTargetRepository targetRepo;

	@Inject
	private EmployeeRecordAdapter empAdapter;

	public List<AggrPeriodTargetDto> findAll(String aggrPeriodId) {
		List<AggrPeriodTargetDto> result = new ArrayList<>();
		List<AggrPeriodTarget> listTarget = targetRepo.findAll(aggrPeriodId);
		List<String> listEmployeeId = listTarget.stream().map(l -> l.getEmployeeId()).collect(Collectors.toList());
		List<EmployeeRecordImport> lstEmpInfo = empAdapter.getPersonInfor(listEmployeeId);
		for (AggrPeriodTarget a : listTarget) {
			EmployeeRecordImport empInfo = lstEmpInfo.stream().filter(e -> e.getEmployeeId().equals(a.getEmployeeId())).collect(Collectors.toList()).get(0);
			AggrPeriodTargetDto dto = new AggrPeriodTargetDto(a.getEmployeeId(), empInfo.getEmployeeCode(),
					empInfo.getPname(), a.getState().name);
			result.add(dto);
		}
		result.sort((AggrPeriodTargetDto c1, AggrPeriodTargetDto c2) -> c1.getEmployeeCode()
				.compareTo(c2.getEmployeeCode()));
		return result;
	}

	public List<PeriodTargetDto> findAllPeriod(String aggrId) {
		return targetRepo.findAll(aggrId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}
	
	/**
	 * Convert To Database Aggr Period Target
	 * @param target
	 * @return
	 */
	private PeriodTargetDto convertToDbType(AggrPeriodTarget target){
		List<String> employeeIds = targetRepo.findAll(target.getAggrId()).stream().map(x -> x.getEmployeeId()).collect(Collectors.toList());
		PeriodTargetDto targetDto = new PeriodTargetDto();
		targetDto.setAggrId(target.getAggrId());
		targetDto.setEmployeeId(employeeIds);
		targetDto.setState(target.getState().value);
		return targetDto;
	}

}
