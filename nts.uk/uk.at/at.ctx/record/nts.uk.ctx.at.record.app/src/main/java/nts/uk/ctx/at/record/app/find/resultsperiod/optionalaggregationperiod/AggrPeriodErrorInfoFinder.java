package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforRepository;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class AggrPeriodErrorInfoFinder {

	@Inject
	private AggrPeriodInforRepository errorInfoRepo;

	@Inject
	private EmployeeRecordAdapter empAdapter;

	public List<AggrPeriodErrorInfoDto> findAll(String aggrPeriodId) {
		List<AggrPeriodErrorInfoDto> result = new ArrayList<>();
		List<AggrPeriodInfor> listErr = errorInfoRepo.findAll(aggrPeriodId);
		for (AggrPeriodInfor err : listErr) {
			EmployeeRecordImport empInfo = empAdapter.getPersonInfor(err.getMemberId());
			AggrPeriodErrorInfoDto dto = new AggrPeriodErrorInfoDto(err.getMemberId(), empInfo.getEmployeeCode(),
					empInfo.getPname(), err.getProcessDay(), err.getErrorMess().v());
			result.add(dto);
		}
		result.sort((AggrPeriodErrorInfoDto c1, AggrPeriodErrorInfoDto c2) -> c1.getEmployeeCode()
				.compareTo(c2.getEmployeeCode()));
		return result;
	}

}
