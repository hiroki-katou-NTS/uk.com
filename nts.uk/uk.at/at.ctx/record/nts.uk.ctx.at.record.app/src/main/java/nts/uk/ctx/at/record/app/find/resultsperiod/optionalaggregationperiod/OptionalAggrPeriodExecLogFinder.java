package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
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

	public List<OptionalAggrPeriodExecLogDto> findLog(GeneralDate start, GeneralDate end) {
		List<OptionalAggrPeriodExecLogDto> result = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		List<AggrPeriodExcution> listLog = logRepo.findExecutionPeriod(companyId, start, end);
		for (AggrPeriodExcution log : listLog) {
			Optional<OptionalAggrPeriod> opt = aggrPeriodRepo.find(companyId, log.getAggrFrameCode().v());
			if (opt.isPresent()) {
				OptionalAggrPeriodExecLogDto dto = new OptionalAggrPeriodExecLogDto(log.getAggrId(),
						log.getAggrFrameCode().v(), opt.get().getOptionalAggrName().v(), log.getStartDateTime(),
						log.getExecutionEmpId(), "AA BB", opt.get().getStartDate(), opt.get().getEndDate(),
						log.getExecutionStatus().name, 1, 1);
				result.add(dto);
			} else {
				OptionalAggrPeriodExecLogDto dto = new OptionalAggrPeriodExecLogDto(log.getAggrId(),
						log.getAggrFrameCode().v(), TextResource.localize("Msg_1307"), log.getStartDateTime(),
						log.getExecutionEmpId(), "AA BB", null, null, log.getExecutionStatus().name, 1, 1);
				result.add(dto);
			}
		}
		return result;
	}
}
