package nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeCondOtRepository;

/**
 * 
 * @author yennth
 *
 */
@Stateless
public class AgreeCondOtFinder {
	@Inject
	private IAgreeCondOtRepository condOtRep;
	
//	public List<AgreeCondOtDto> finder(){
//		List<AgreeCondOt> result = condOtRep.findAll();
//		return result.stream().map(x -> {
//			return new AgreeCondOtDto(x.getId(), x.getNo(), x.getCode().v(), 
//					x.getOt36().v(), x.getExcessNum().v(), x.getMessageDisp().v());
//		}).collect(Collectors.toList());
//	}
}
