package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.workchange.IWorkChangeRegisterService;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.IAppWorkChangeSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppWorkChangeSetFinder {
	
	@Inject
	private IAppWorkChangeSetRepository appWRep;
	
	@Inject 
	private IWorkChangeRegisterService workChangeRegisterService;
	
	public AppWorkChangeSetDto findByCom(){
		String companyId = AppContexts.user().companyId();
		Optional<AppWorkChangeSet> app = appWRep.findWorkChangeSetByID(companyId);
		if(app.isPresent()){
			return AppWorkChangeSetDto.fromDomain(app.get());
		}
		return null;
	}
	
	public boolean isTimeRequired(String workTypeCD){
		return workChangeRegisterService.isTimeRequired(workTypeCD);
	}
}
