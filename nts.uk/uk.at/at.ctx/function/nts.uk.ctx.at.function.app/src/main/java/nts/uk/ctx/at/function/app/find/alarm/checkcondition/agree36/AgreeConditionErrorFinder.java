package nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeConditionErrorRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeNameErrorRepository;

/**
 * 
 * @author yennth
 *
 */
@Stateless
public class AgreeConditionErrorFinder {
	@Inject
	private IAgreeConditionErrorRepository errorRep;

	@Inject
	private IAgreeNameErrorRepository nameRep;

//	public List<AgreeConditionErrorDto> finder() {
//		List<AgreeConditionError> listConError = errorRep.findAll();
//		List<AgreeNameError> listAgreeNameError = this.nameRep.findAll();
//
//		return listConError.stream().map(item -> {
//			String agreementNameErr = listAgreeNameError.stream()
//					.filter(x -> (x.getPeriod() == item.getPeriod() && x.getErrorAlarm() == item.getErrorAlarm()))
//					.findFirst().get().getName().v();
//			return new AgreeConditionErrorDto(item.getId(), item.getCode().v(), 
//					item.getUseAtr().value, item.getPeriod().value,
//					item.getErrorAlarm().value, item.getMessageDisp().v(), agreementNameErr);
//		}).collect(Collectors.toList());
//	}
}
