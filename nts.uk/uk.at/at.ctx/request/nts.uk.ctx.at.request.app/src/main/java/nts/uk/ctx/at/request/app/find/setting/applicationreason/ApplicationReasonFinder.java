package nts.uk.ctx.at.request.app.find.setting.applicationreason;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class ApplicationReasonFinder {

	@Inject
	ApplicationReasonRepository appFormRepo;

	/**
	 * 
	 * @param appType
	 * @return
	 */
	public List<ApplicationReason> getListApplicationReason(int appType) {
		String companyId = AppContexts.user().companyId();
		List<ApplicationReason> listReason = appFormRepo.getReasonByAppType(companyId, appType);
		return listReason;
	}
}
