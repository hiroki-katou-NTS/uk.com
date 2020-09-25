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
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalBehaviorAtrHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalFormHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalFrameHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalPhaseStateHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalRootContentHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalRootStateHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApproverStateHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ErrorFlagHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalRootStateHrRepository;
import nts.uk.ctx.workflow.pub.hrapprovalstate.ApprovalStateHrPub;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.ApprovalStateHrImport;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.ApproverInfoHrImport;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.FrameHrImport;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.PhaseStateHrImport;
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
public class ApprovalRootStateImpRepository implements ApprovalRootStateHrRepository {

	@Inject
	private ApprovalRootStatePub approvalRootStatePub;
	
	@Inject
	private ApprovalStateHrPub approvalStateHrPub;

	
    /**
     * Imp REQ [No.309]承認ルートを取得する cua hungnv
     */
	@Override
	public ApprovalRootContentHrExport getApprovalRootHr(String companyID, String employeeID, String targetType,
			GeneralDate date, Optional<Boolean> lowerApprove) {

		ApprovalRootContentExport export = approvalRootStatePub.getApprovalRootHr(companyID, employeeID, targetType,
				date, lowerApprove);
		
		ApprovalRootContentHrExport output = new ApprovalRootContentHrExport();
		convertData(export, output);
		return output;
	}
	
	
	/**
	 * Imp [RQ637]承認ルートインスタンスを新規作成する của hoatt
	 */
	@Override
	public boolean createApprStateHr(ApprovalRootContentHrExport apprSttHr, String rootStateID, String employeeID, GeneralDate appDate) {
		
		ApprovalStateHrImport inputReq637 = convertDataReq637(apprSttHr, rootStateID,  employeeID,  appDate);
		boolean result = approvalStateHrPub.createApprStateHr(inputReq637);
		return result;
	}
	
	private void convertData(ApprovalRootContentExport input, ApprovalRootContentHrExport output){
		
		output.setErrorFlag(EnumAdaptor.valueOf(input.getErrorFlag().value, ErrorFlagHrExport.class));
		output.setApprovalRootState(null);
		
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
								approverStateHrExportOutput.setApprovalDate(GeneralDate.localDate(approverStateExportInput.getApprovalDate().toLocalDate()));
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
		
		ApprovalRootStateHrExport approvalRootStateHrExport = new ApprovalRootStateHrExport(listApprovalPhaseStateOutput);
		output.setApprovalRootState(approvalRootStateHrExport);
		
	}
	
	private ApprovalStateHrImport convertDataReq637(ApprovalRootContentHrExport input  , String rootStateID, String employeeID, GeneralDate appDate){
			
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
			
			ApprovalStateHrImport result = new ApprovalStateHrImport(rootStateID, appDate, employeeID, false, lstPhaseState);
			return result;
		}

}
