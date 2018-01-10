package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class DetailAfterReleaseImpl implements DetailAfterRelease {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Override
	public void detailAfterRelease(String companyID, String appID, String loginID, String memo) {
		Application_New application = applicationRepository.findByID(companyID, appID).get();
		Boolean releaseFlg = approvalRootStateAdapter.doRelease(companyID, appID, loginID);
		approvalRootStateAdapter.updateReason(appID, loginID, "");
		if(releaseFlg.equals(Boolean.TRUE)){
			application.getReflectionInformation().setStateReflectionReal(ReflectedState_New.NOTREFLECTED);
			applicationRepository.updateWithVersion(application);
		}
	}
}
