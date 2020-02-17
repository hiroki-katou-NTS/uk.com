/**
 * 
 */
package nts.uk.ctx.hr.shared.ac.approval.rootstate;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalFrameHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalPhaseStateHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalRootContentHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApproverStateHrExport;
import nts.uk.ctx.hr.shared.dom.create.approval.rootstate.ICreateApprovalStateAdaptor;
import nts.uk.ctx.workflow.pub.hrapprovalstate.ApprovalStateHrPub;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.ApprovalStateHrImport;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.ApproverInfoHrImport;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.FrameHrImport;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.PhaseStateHrImport;

/**
 * @author laitv
 *
 */
@Stateless
public class CreateApprovalStateAdaptor implements ICreateApprovalStateAdaptor{
	
	@Inject
	private ApprovalStateHrPub approvalStateHrPub;

	
	@Override
	public boolean createApprStateHr(ApprovalRootContentHrExport apprSttHr, String rootStateID, String employeeID, GeneralDate appDate) {
		
		ApprovalStateHrImport inputReq637 = convertData(apprSttHr, rootStateID,  employeeID,  appDate);
		boolean result = approvalStateHrPub.createApprStateHr(inputReq637);
		return result;
	}
	
	private ApprovalStateHrImport convertData(ApprovalRootContentHrExport input  , String rootStateID, String employeeID, GeneralDate appDate){
		
		List<PhaseStateHrImport> lstPhaseState = new ArrayList<PhaseStateHrImport>();
		List<ApprovalPhaseStateHrExport> listApprovalPhaseState = input.getApprovalRootState() != null ? input.getApprovalRootState().getListApprovalPhaseState() : new ArrayList<>();
		if (!listApprovalPhaseState.isEmpty()) {
			for (int i = 0; i < listApprovalPhaseState.size(); i++) {
				ApprovalPhaseStateHrExport approvalPhaseStateHrExport = listApprovalPhaseState.get(i);
				 Integer phaseOrder = approvalPhaseStateHrExport.getPhaseOrder();
				 int approvalAtr    = approvalPhaseStateHrExport.getApprovalAtr().value;
				 int approvalForm   = approvalPhaseStateHrExport.getApprovalForm().value;
				 List<FrameHrImport> lstApprovalFrame = new ArrayList<>();
				 List<ApprovalFrameHrExport> listApprovalFrameInput = approvalPhaseStateHrExport.getListApprovalFrame();
				if (!listApprovalFrameInput.isEmpty()) {
					for (int j = 0; j < listApprovalFrameInput.size(); j++) {
						ApprovalFrameHrExport approvalFrameHrExport = listApprovalFrameInput.get(j);
						int frameOrder = approvalFrameHrExport.getFrameOrder();
						int confirmAtr = approvalFrameHrExport.getConfirmAtr();
						
						List<ApproverInfoHrImport> lstApproverInfo = new ArrayList<>();
						List<ApproverStateHrExport> listApprove = approvalFrameHrExport.getListApprover();
						if (!listApprove.isEmpty()) {
							for (int k = 0; k < listApprove.size(); k++) {
								ApproverStateHrExport ApproverStateHrExport = listApprove.get(k);
								String approverID = ApproverStateHrExport.getApproverID();
								int approvalAtr2 = ApproverStateHrExport.getApprovalAtr().value;
								String agentID = ApproverStateHrExport.getAgentID();
								GeneralDate approvalDate = ApproverStateHrExport.getApprovalDate();
								String approvalReason = ApproverStateHrExport.getApprovalReason();
								ApproverInfoHrImport ApproverInfoHrImport = new ApproverInfoHrImport(approverID, approvalAtr2, agentID, approvalDate, approvalReason);
								lstApproverInfo.add(ApproverInfoHrImport);
							}
						}
						FrameHrImport frameHrImport = new FrameHrImport(frameOrder, confirmAtr, appDate, lstApproverInfo);
						lstApprovalFrame.add(frameHrImport);
					}
				}
				 
				 PhaseStateHrImport phaseStateHrImport = new PhaseStateHrImport(phaseOrder, approvalAtr, approvalForm, lstApprovalFrame);
				 lstPhaseState.add(phaseStateHrImport);
			}
		}
		
		ApprovalStateHrImport result = new ApprovalStateHrImport(rootStateID, appDate, employeeID, lstPhaseState);
		return result;
	}
}
