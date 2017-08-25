package nts.uk.ctx.at.request.app.find.application.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
//import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalATR;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ActualReflectionStateDecision {
	
	@Inject
	private AppApprovalPhaseRepository approvalPhaseRepo;
	
	@Inject 
	private ApprovalFrameRepository frameRepo;
	
	//承認者一覧を取得する
	// 1 : 承認者一覧
	// 2 : 未承認一覧
	public List<String> AcquireApproverList(String appID, int approved) {
		//
		//OUTPUT LIST APPROVER
		//
		// get List 5 承認 Phase
		String companyID = AppContexts.user().companyId();
		//get list Phase
		List<AppApprovalPhase> listPhase = approvalPhaseRepo.findPhaseByAppID(companyID, appID);
		//get List Frame
		//List<ApprovalFrame> listFrame = frameRepo.findByPhaseID(companyID, phaseID);
		//承認者一覧
		List<String> lstApprover = new ArrayList<>();
		List<String> lstNotApprover = new ArrayList<>();
		//Loop 5 phase 
		for(AppApprovalPhase phase : listPhase) {
			//get List Frame
			List<ApprovalFrame> listFrame = frameRepo.findByPhaseID(companyID, phase.getPhaseID());
			for(ApprovalFrame frame : listFrame) {
				if(frame.getApprovalATR() == ApprovalATR.APPROVED) {
					lstApprover.add(frame.getApproverSID());
				}else {
					lstNotApprover.add(frame.getApproverSID());
				}
			}
		}
		//Get distinct List Approver
		lstApprover.stream().distinct().collect(Collectors.toList());
		lstNotApprover.stream().distinct().collect(Collectors.toList());
		if(approved ==1) {
			return lstApprover;
		}else {
			return lstNotApprover;
		}
	}
}
