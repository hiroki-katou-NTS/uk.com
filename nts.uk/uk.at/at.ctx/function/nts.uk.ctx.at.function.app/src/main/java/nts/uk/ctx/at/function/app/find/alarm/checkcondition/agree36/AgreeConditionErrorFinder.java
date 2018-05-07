package nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeConditionError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeConditionErrorRepository;

/**
 * 
 * @author yennth
 *
 */
@Stateless
public class AgreeConditionErrorFinder {
	@Inject
	private IAgreeConditionErrorRepository errorRep;
	
	public List<AgreeConditionErrorDto> finder(){
		List<AgreeConditionError> listConError = errorRep.findAll();
		return listConError.stream().map(item -> {
			return new AgreeConditionErrorDto(item.getId(), item.getUseAtr().value, item.getPeriod().value, item.getErrorAlarm().value, item.getMessageDisp().v());
		}).collect(Collectors.toList());
	}
}
