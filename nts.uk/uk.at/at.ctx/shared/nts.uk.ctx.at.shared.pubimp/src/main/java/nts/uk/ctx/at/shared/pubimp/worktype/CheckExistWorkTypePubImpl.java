package nts.uk.ctx.at.shared.pubimp.worktype;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.pub.worktype.CheckExistWorkTypePub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckExistWorkTypePubImpl implements CheckExistWorkTypePub {

	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Override
	public boolean checkExitsWorkType(String workTypeCD) {
		String companyID = 	AppContexts.user().companyId();
		return workTypeRepository.findByDeprecated(companyID, workTypeCD).isPresent();
	}

}
