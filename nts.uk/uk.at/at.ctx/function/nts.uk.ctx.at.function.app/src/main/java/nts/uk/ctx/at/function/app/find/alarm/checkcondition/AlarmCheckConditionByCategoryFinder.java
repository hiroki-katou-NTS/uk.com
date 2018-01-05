package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */
@Stateless
public class AlarmCheckConditionByCategoryFinder {
	
	@Inject
	private AlarmCheckConditionByCategoryRepository conditionRepo;
	
	public List<AlarmCheckConditionByCategoryDto> getAllData(int category){
		String companyId = AppContexts.user().companyId();
		return conditionRepo.findByCategory(companyId, category).stream().map(item -> AlarmCheckConditionByCategoryDto.fromDomain(item)).collect(Collectors.toList());
	}

}
