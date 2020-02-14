/**
 * 
 */
package nts.uk.ctx.hr.shared.ac.approval.rootstate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalBehaviorAtrHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalFormHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalFrameHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalPhaseStateHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalRootContentHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApproverStateHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ErrorFlagHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.IApprovalRootStateAdaptor;
import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
import nts.uk.ctx.workflow.pub.service.export.ApprovalFrameExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalPhaseStateExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootContentExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootStateExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverStateExport;

/**
 * @author laitv
 *
 */
@Stateless
public class ApprovalRootStateAdaptor implements IApprovalRootStateAdaptor {

	@Inject
	private ApprovalRootStatePub approvalRootStatePub;

	@Override
	public ApprovalRootContentHrExport getApprovalRootHr(String companyID, String employeeID, String targetType,
			GeneralDate date, Optional<Boolean> lowerApprove) {

		ApprovalRootContentExport export = approvalRootStatePub.getApprovalRootHr(companyID, employeeID, targetType,
				date, lowerApprove);
		
		ApprovalRootContentHrExport result = new ApprovalRootContentHrExport();
		convertData(export, result);
		return result;
	}
	
	private void convertData(ApprovalRootContentExport input, ApprovalRootContentHrExport output){
		
		output.setErrorFlag(EnumAdaptor.valueOf(input.getErrorFlag().value, ErrorFlagHrExport.class));
		
		ApprovalRootStateExport approvalRootStateInput = input.getApprovalRootState();
		List<ApprovalPhaseStateExport> listApprovalPhaseStateInput = approvalRootStateInput.getListApprovalPhaseState();
		List<ApprovalPhaseStateHrExport> listApprovalPhaseStateOutput = new ArrayList<ApprovalPhaseStateHrExport>();
		if (!listApprovalPhaseStateInput.isEmpty()) {
			for (int i = 0; i < listApprovalPhaseStateInput.size(); i++) {
				ApprovalPhaseStateExport approvalPhaseStateInput = listApprovalPhaseStateInput.get(i);
				ApprovalPhaseStateHrExport approvalPhaseStateOutput = new ApprovalPhaseStateHrExport(); 
				// set data
				approvalPhaseStateOutput.setPhaseOrder(approvalPhaseStateInput.getPhaseOrder());
				approvalPhaseStateOutput.setApprovalAtr(EnumAdaptor.valueOf(approvalPhaseStateInput.getApprovalAtr().value, ApprovalBehaviorAtrHrExport.class));
				approvalPhaseStateOutput.setApprovalForm(EnumAdaptor.valueOf(approvalPhaseStateInput.getApprovalForm().value, ApprovalFormHrExport.class));
				
				List<ApprovalFrameExport> listApprovalFrameInput = approvalPhaseStateInput.getListApprovalFrame();
				List<ApprovalFrameHrExport> listApprovalFrameOutPut = new ArrayList<>();
				if (!listApprovalFrameInput.isEmpty()) {
					for (int j = 0; j < listApprovalFrameInput.size(); j++) {
						ApprovalFrameExport approvalFrameInput = listApprovalFrameInput.get(j);
						ApprovalFrameHrExport approvalFrameOutput = new ApprovalFrameHrExport();
						
						approvalFrameOutput.setFrameOrder(approvalFrameInput.getFrameOrder());
						approvalFrameOutput.setConfirmAtr(approvalFrameInput.getConfirmAtr());
						approvalFrameOutput.setAppDate(approvalFrameInput.getAppDate());
						
						List<ApproverStateExport> listApproverInput = approvalFrameInput.getListApprover();
						List<ApproverStateHrExport> listApproverOutput = new ArrayList<>();
						if (!listApproverInput.isEmpty()) {
							for (int k = 0; k < listApproverInput.size(); k++) {
								ApproverStateExport  approverStateExportInput = listApproverInput.get(k);
								ApproverStateHrExport approverStateHrExportOutput = new  ApproverStateHrExport();
								
								approverStateHrExportOutput.setApproverID(approverStateExportInput.getApproverID());
								approverStateHrExportOutput.setApprovalAtr(EnumAdaptor.valueOf(approverStateExportInput.getApprovalAtr().value, ApprovalBehaviorAtrHrExport.class));
								approverStateHrExportOutput.setAgentID(approverStateExportInput.getAgentID());
								approverStateHrExportOutput.setApproverName(approverStateExportInput.getApproverName());
								approverStateHrExportOutput.setRepresenterID(approverStateExportInput.getRepresenterID());
								approverStateHrExportOutput.setRepresenterName(approverStateExportInput.getRepresenterName());
								approverStateHrExportOutput.setApprovalDate(approverStateExportInput.getApprovalDate());
								approverStateHrExportOutput.setApprovalReason(approverStateExportInput.getApprovalReason());
								
								listApproverOutput.add(approverStateHrExportOutput);
							}
						}
						
						approvalFrameOutput.setListApprover(listApproverOutput);
						listApprovalFrameOutPut.add(approvalFrameOutput);
					}
				}
				
				
				approvalPhaseStateOutput.setListApprovalFrame(listApprovalFrameOutPut);
				listApprovalPhaseStateOutput.add(approvalPhaseStateOutput);
			}
		}
	}

}
