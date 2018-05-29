package nts.uk.ctx.at.function.dom.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutItemsWoScRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SetOutItemsWoScService implements AnnualWorkScheduleService {
	@Inject
	private SetOutItemsWoScRepository repo;
	
	@Override
	public boolean checkDuplicateCode(String itemOutSettingCode){
		if(repo.getSetOutItemsWoScById(AppContexts.user().companyId(), itemOutSettingCode).isPresent()){
			return true;
		}
		return false;
	}
}
