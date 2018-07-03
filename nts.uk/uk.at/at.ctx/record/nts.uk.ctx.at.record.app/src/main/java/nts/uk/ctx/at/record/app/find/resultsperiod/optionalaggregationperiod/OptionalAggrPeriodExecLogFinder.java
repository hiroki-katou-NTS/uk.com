package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTargetRepository;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class OptionalAggrPeriodExecLogFinder {

	@Inject
	private AggrPeriodExcutionRepository logRepo;

	@Inject
	private OptionalAggrPeriodRepository aggrPeriodRepo;

	@Inject
	private AggrPeriodTargetRepository targetRepo;

	@Inject
	private AggrPeriodInforRepository errorInfoRepo;

	@Inject
	private EmployeeRecordAdapter empAdapter;

	public List<OptionalAggrPeriodExecLogDto> findLog(GeneralDate start, GeneralDate end) {
		List<OptionalAggrPeriodExecLogDto> result = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		List<AggrPeriodExcution> listLog = logRepo.findExecutionPeriod(companyId, start, end);
		for (AggrPeriodExcution log : listLog) {
			Optional<OptionalAggrPeriod> optAggr = aggrPeriodRepo.find(companyId, log.getAggrFrameCode().v());
			List<AggrPeriodTarget> listTarget = targetRepo.findAll(log.getAggrId());
			List<AggrPeriodInfor> listError = errorInfoRepo.findAll(log.getAggrId());
			EmployeeRecordImport empInfo = empAdapter.getPersonInfor(log.getExecutionEmpId());
			if (optAggr.isPresent()) {
				OptionalAggrPeriodExecLogDto dto = new OptionalAggrPeriodExecLogDto(log.getAggrId(),
						log.getAggrFrameCode().v(), optAggr.get().getOptionalAggrName().v(), log.getStartDateTime(),
						log.getExecutionEmpId(), empInfo.getPname(), optAggr.get().getStartDate(),
						optAggr.get().getEndDate(), log.getExecutionStatus().get().name, listTarget.size(), listError.size());
				result.add(dto);
			} else {
				OptionalAggrPeriodExecLogDto dto = new OptionalAggrPeriodExecLogDto(log.getAggrId(),
						log.getAggrFrameCode().v(), TextResource.localize("Msg_1307"), log.getStartDateTime(),
						log.getExecutionEmpId(), empInfo.getPname(), null, null, log.getExecutionStatus().get().name,
						listTarget.size(), listError.size());
				result.add(dto);
			}
		}
		result.sort((OptionalAggrPeriodExecLogDto c1, OptionalAggrPeriodExecLogDto c2) -> c2.getExecutionDt()
				.compareTo(c1.getExecutionDt()));
		return result;
	}
	
	public AggrPeriodExcutionDto findAggr(String aggrFrameCode){
		String companyId = AppContexts.user().companyId();
		String executionEmpId = AppContexts.user().employeeId();
		Optional<AggrPeriodExcution> data = this.logRepo.findExecution(companyId, executionEmpId, aggrFrameCode);

		if (data.isPresent()) {
			return AggrPeriodExcutionDto.fromDomain(data.get());
		}

		return null;
		
	}
	
	public AggrPeriodExcutionDto findStatus(String aggrFrameCode, int executionStatus){
		String companyId = AppContexts.user().companyId();
		Optional<AggrPeriodExcution> data = this.logRepo.findStatus(companyId, aggrFrameCode, executionStatus);

		if (data.isPresent()) {
			return AggrPeriodExcutionDto.fromDomain(data.get());
		}

		return null;
		
	}
	
	public AggrPeriodExcutionDto findAll(String aggrFrameCode) {
		String companyId = AppContexts.user().companyId();
		Optional<AggrPeriodExcution> data = this.logRepo.findAggrCode(companyId, aggrFrameCode);

		if (data.isPresent()) {
			return AggrPeriodExcutionDto.fromDomain(data.get());
		}

		return null;
	}
	
}
