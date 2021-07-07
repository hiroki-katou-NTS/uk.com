package nts.uk.screen.at.app.query.kdp.kdp002.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetWorkManagementMultiple {

	@Inject
	private WorkManagementMultipleRepository getWorkManagementMultiple;
	
	public boolean getWorkManagementMultiple() {
		
		String cid = AppContexts.user().companyId();
		
		Optional<WorkManagementMultiple> workManagementMultiple = this.getWorkManagementMultiple.findByCode(cid);
		
		if (!workManagementMultiple.isPresent()){
			return false;
		}
		
		return workManagementMultiple.get().getUseATR().value == 0 ? false : true;
		
	}
	
}
