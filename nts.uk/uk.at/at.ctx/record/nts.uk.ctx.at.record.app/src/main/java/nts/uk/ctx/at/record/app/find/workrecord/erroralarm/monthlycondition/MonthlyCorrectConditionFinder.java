/**
 * 4:00:31 PM Mar 28, 2018
 */
package nts.uk.ctx.at.record.app.find.workrecord.erroralarm.monthlycondition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.MonthlyCorrectConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.TimeItemCheckMonthly;

/**
 * @author hungnm
 *
 */
@Stateless
public class MonthlyCorrectConditionFinder {

	@Inject
	private MonthlyCorrectConditionRepository monthlyCorrectConditionRepository;

	public List<MonthlyCorrectConditionDto> findAllMonthlyCorrectCondition() {
		return monthlyCorrectConditionRepository.findAllMonthlyConditionByCompanyId().stream()
				.map(domain -> MonthlyCorrectConditionDto.fromDomain(domain, null)).collect(Collectors.toList());
	}
	
	public MonthlyCorrectConditionDto getMonthlyCorrectConditionDto(String checkId, String errCode) {
		TimeItemCheckMonthly timeItemCheckMonthly = monthlyCorrectConditionRepository.findTimeItemCheckMonthlyById(checkId, errCode).get();
		return MonthlyCorrectConditionDto.fromDomain(null, timeItemCheckMonthly);
	}

	public List<MonthlyCorrectConditionDto> findUseMonthlyCorrectCondition() {
		return monthlyCorrectConditionRepository.findUseMonthlyConditionByCompanyId().stream()
				.map(domain -> MonthlyCorrectConditionDto.fromDomain(domain, null)).collect(Collectors.toList());
	}
	
}
