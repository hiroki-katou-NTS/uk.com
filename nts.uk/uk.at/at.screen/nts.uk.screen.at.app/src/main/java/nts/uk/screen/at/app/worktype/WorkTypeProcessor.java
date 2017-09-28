package nts.uk.screen.at.app.worktype;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkTypeProcessor {

	@Inject
	private WorkTypeQueryRepository workTypeQueryRepository;
	
	public List<WorkTypeDto> findWorkTypeAll(){
		String companyId = AppContexts.user().companyId();
		return this.workTypeQueryRepository.findAllWorkType(companyId);
	}

}
