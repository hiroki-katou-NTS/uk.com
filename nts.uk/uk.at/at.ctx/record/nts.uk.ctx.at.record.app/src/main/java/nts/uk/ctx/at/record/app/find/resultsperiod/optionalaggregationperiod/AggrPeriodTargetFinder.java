package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import java.util.ArrayList;
import java.util.List;

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
		for (AggrPeriodTarget a : listTarget) {
			EmployeeRecordImport empInfo = empAdapter.getPersonInfor(a.getMemberId());
			AggrPeriodTargetDto dto = new AggrPeriodTargetDto(a.getMemberId(), empInfo.getEmployeeCode(),
					empInfo.getPname(), a.getState().name);
			result.add(dto);
		}
		result.sort((AggrPeriodTargetDto c1, AggrPeriodTargetDto c2) -> c1.getEmployeeCode()
				.compareTo(c2.getEmployeeCode()));
		return result;
	}

}
