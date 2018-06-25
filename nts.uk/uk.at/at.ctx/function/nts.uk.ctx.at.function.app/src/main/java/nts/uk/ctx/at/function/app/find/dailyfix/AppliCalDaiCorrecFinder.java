package nts.uk.ctx.at.function.app.find.dailyfix;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.dailyfix.ApplicationCall;
import nts.uk.ctx.at.function.dom.dailyfix.IAppliCalDaiCorrecRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppliCalDaiCorrecFinder {
	
	@Inject
	private IAppliCalDaiCorrecRepository appCalRep;
	
	public AppliCalDaiCorrecDto find(){
		String companyId = AppContexts.user().companyId();
		List<ApplicationCall> appCal = this.appCalRep.findByCom(companyId);
		return new AppliCalDaiCorrecDto(appCal.stream()
										.map(x -> x.getAppType().value).collect(Collectors.toList()));
	} 
}
