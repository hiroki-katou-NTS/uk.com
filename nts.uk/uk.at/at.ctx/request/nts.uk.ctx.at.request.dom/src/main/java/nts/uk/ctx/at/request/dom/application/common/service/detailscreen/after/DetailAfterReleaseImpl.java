package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ConfirmAtr;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenProcessAfterOutput;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class DetailAfterReleaseImpl implements DetailAfterRelease {
	
	@Inject 
	private AfterApprovalProcess afterApprovalProcess;
	
	@Inject
	private ApplicationRepository appRepo;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Override
	public void detailAfterRelease(String companyID, String appID, String loginID) {
		Application_New application = applicationRepository.findByID(companyID, appID).get();
		Boolean releaseFlg = approvalRootStateAdapter.doRelease(companyID, appID, loginID);
		if(releaseFlg.equals(Boolean.TRUE)){
			application.getReflectionInformation().setStateReflectionReal(ReflectedState_New.NOTREFLECTED);
			applicationRepository.updateWithVersion(application);
		}
	}
}
