package nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeNameError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeNameErrorRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.ParamFind;

/**
 * 
 * @author yennth
 *
 */
@Stateless
public class AgreeNameErrorFinder {
	@Inject
	private IAgreeNameErrorRepository nameRep;
	public List<AgreeNameErrorDto> finder(ParamFindName param){
		List<AgreeNameError> result = nameRep.findName(param.getParamList());
		return result.stream().map(x -> {
			return new AgreeNameErrorDto(x.getPeriod().value, x.getErrorAlarm().value, x.getName().v());
		}).collect(Collectors.toList());
	}
}
