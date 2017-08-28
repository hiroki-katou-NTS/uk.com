package nts.uk.ctx.at.request.dom.application.common.newscreenregisteratapprovereflectionInfo.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;

@Stateless
public class NewScreenRegisterAtApproveReflectionInfoDefalt implements NewScreenRegisterAtApproveReflectionInfoService {

	@Inject
	private ApplicationRepository appRepo;

	@Inject
	private AppApprovalPhaseRepository approvalPhaseRepo;

	@Inject
	private ApprovalFrameRepository frameRepo;

	// @Inject
	//private DetailedScreenAfterApprovalProcess approvalProcess;

	@Override
	public void newScreenRegisterAtApproveInfoReflect(String empID, Application application) {
		// TODO Auto-generated method stub

	}

	@Override
	public void organizationOfApprovalInfo(String appID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void performanceReflectedStateJudgment(String appID) {
		// TODO Auto-generated method stub

	}

}
