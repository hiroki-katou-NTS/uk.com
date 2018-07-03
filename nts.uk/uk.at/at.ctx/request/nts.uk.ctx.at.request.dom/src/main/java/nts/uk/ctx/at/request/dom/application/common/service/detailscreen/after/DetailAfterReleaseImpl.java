package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

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
	public ProcessResult detailAfterRelease(String companyID, String appID, String loginID, String memo) {
		boolean isProcessDone = false;
		boolean isAutoSendMail = false;
		List<String> autoSuccessMail = new ArrayList<>();
		List<String> autoFailMail = new ArrayList<>();
		Application_New application = applicationRepository.findByID(companyID, appID).get();
		Boolean releaseFlg = approvalRootStateAdapter.doRelease(companyID, appID, loginID);
		if(releaseFlg.equals(Boolean.TRUE)){
			isProcessDone = true;
			application.getReflectionInformation().setStateReflectionReal(ReflectedState_New.NOTREFLECTED);
			applicationRepository.updateWithVersion(application);
		}
		return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, appID, "");
	}
}
