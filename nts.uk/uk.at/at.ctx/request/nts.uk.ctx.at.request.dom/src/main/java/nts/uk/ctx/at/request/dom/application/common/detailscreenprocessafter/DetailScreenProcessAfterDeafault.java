package nts.uk.ctx.at.request.dom.application.common.detailscreenprocessafter;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.detailedacreenafterapprovalprocess.service.DetailedScreenAfterApprovalProcessService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class DetailScreenProcessAfterDeafault implements DetailScreenProcessAfterService {
	/**10-2 */
	@Inject
	private ApplicationRepository applicationRepository;

	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;
	
	@Inject
	private ApprovalFrameRepository approvalFrameRepository;
	
	@Inject
	DetailedScreenAfterApprovalProcessService detailedScreenAfterApprovalProcessService;
	
	@Override
	public DetailScreenProcessAfterOutput getDetailScreenProcessAfter(Application applicationData) {

		String companyID = AppContexts.user().companyId();
		String appID = applicationData.getApplicationID();
		
		List<AppApprovalPhase> listAppApprovalPhase = appApprovalPhaseRepository.findPhaseByAppID(companyID, appID);
		
		for(AppApprovalPhase appApprovalPhase : listAppApprovalPhase) {
			//List<String> listApproverID = detailedScreenAfterApprovalProcessService.actualReflectionStateDecision(appID, appApprovalPhase.getPhaseID(), appApprovalPhase.getApprovalATR());
			List<ApprovalFrame> listApprovalFrame = approvalFrameRepository.getAllApproverByPhaseID(companyID, appApprovalPhase.getPhaseID());
			if (!listApprovalFrame.isEmpty()) {
				if (listApprovalFrame.stream().filter(f -> f.getApprovalATR() != ApprovalAtr.UNAPPROVED).count() > 0) {
					
				}
			}
		}
		
		return null;
	}


	
	

	
}
