package nts.uk.ctx.at.schedule.app.find.shift.basicworkregister;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.WorkplaceBasicWorkFindDto;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class WorkplaceBasicWorkFinder {

	@Inject
	private WorkplaceBasicWorkRepository repository;
	
	public WorkplaceBasicWorkFindDto find() {
		// get login user info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companyId by user login
		String companyId = loginUserContext.companyId();
		
		WorkplaceBasicWorkFindDto outputData = new WorkplaceBasicWorkFindDto();
		
		
		
		return outputData;
	}
}
