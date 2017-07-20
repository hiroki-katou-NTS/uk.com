package nts.uk.ctx.at.schedule.app.find.shift.basicworkregister;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.CompanyBasicWorkFindDto;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class CompanyBasicWorkFinder {

	@Inject
	private CompanyBasicWorkRepository repository;
	
	public CompanyBasicWorkFindDto find() {
		// get login user info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companyId by user login
		String companyId = loginUserContext.companyId();
		
		CompanyBasicWorkFindDto outputData = new CompanyBasicWorkFindDto();
		
		Optional<CompanyBasicWork> companyBasicWork = this.repository.find(companyId);
		
		if(!companyBasicWork.isPresent()) {
			return null;
		}
		
		companyBasicWork.get().saveToMemento(outputData);
		
		return outputData;
	}
	
}
