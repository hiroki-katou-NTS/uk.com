package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.IAppWorkChangeSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppWorkChangeSetFinder {
	@Inject
	private IAppWorkChangeSetRepository appWRep;
	public AppWorkChangeSetDto findByCom(){
		String companyId = AppContexts.user().companyId();
		Optional<AppWorkChangeSet> app = appWRep.findWorkChangeSetByID(companyId);
		if(app.isPresent()){
			return AppWorkChangeSetDto.fromDomain(app.get());
		}
		return null;
	}
}
