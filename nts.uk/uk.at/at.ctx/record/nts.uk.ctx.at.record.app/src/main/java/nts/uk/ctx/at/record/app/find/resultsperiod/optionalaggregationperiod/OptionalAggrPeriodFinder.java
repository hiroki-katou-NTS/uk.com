package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class OptionalAggrPeriodFinder {

	@Inject
	private OptionalAggrPeriodRepository repository;

	/**
	 * 
	 * @return
	 */
	public List<OptionalAggrPeriodDto> findAll() {
		String companyId = AppContexts.user().companyId();
		return repository.findAll(companyId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}

	public OptionalAggrPeriodDto find(String aggrFrameCode) {
		String companyId = AppContexts.user().companyId();

		Optional<OptionalAggrPeriod> data = this.repository.find(companyId, aggrFrameCode);

		if (data.isPresent()) {
			return OptionalAggrPeriodDto.fromDomain(data.get());
		}
		return null;
	}

	/**
	 * 
	 * @param optionalAggrPeriod
	 * @return
	 */
	private OptionalAggrPeriodDto convertToDbType(OptionalAggrPeriod optionalAggrPeriod) {
		OptionalAggrPeriodDto aggrPeriodDto = new OptionalAggrPeriodDto();
		aggrPeriodDto.setCompanyId(optionalAggrPeriod.getCompanyId());
		aggrPeriodDto.setAggrFrameCode(optionalAggrPeriod.getAggrFrameCode().v());
		aggrPeriodDto.setOptionalAggrName(optionalAggrPeriod.getOptionalAggrName().v());
		aggrPeriodDto.setStartDate(optionalAggrPeriod.getStartDate());
		aggrPeriodDto.setEndDate(optionalAggrPeriod.getEndDate());
		return aggrPeriodDto;
	}

}
