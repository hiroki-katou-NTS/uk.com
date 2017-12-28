package nts.uk.ctx.at.request.ac.workflow.approvalrootstate;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootContentExport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApprovalRootStateAdapterImpl implements ApprovalRootStateAdapter {
	
	@Inject
	private ApprovalRootStatePub approvalRootStatePub;
	
	@Override
	public ApprovalRootContentImport_New getApprovalRootContent(String companyID, String employeeID, Integer appTypeValue, GeneralDate appDate, String appID, Boolean isCreate) {
		ApprovalRootContentExport approvalRootContentExport = approvalRootStatePub.getApprovalRoot(companyID, employeeID, appTypeValue, appDate, appID, isCreate);
		return new ApprovalRootContentImport_New(
					new ApprovalRootStateImport_New(
						approvalRootContentExport.getApprovalRootState().getListApprovalPhaseState().stream()
						.map(x -> {
							return new ApprovalPhaseStateImport_New(
									x.getPhaseOrder(), 
									x.getApprovalAtr(), 
									x.getListApprovalFrame().stream()
									.map(y -> {
										return new ApprovalFrameImport_New(
												y.getPhaseOrder(), 
												y.getFrameOrder(), 
												y.getApprovalAtr(), 
												y.getListApprover().stream().map(z -> new ApproverStateImport_New(z.getApproverID(), z.getRepresenterID())).collect(Collectors.toList()), 
												y.getApproverID(), 
												y.getRepresenterID(), 
												y.getApprovalReason());
									}).collect(Collectors.toList()));
						}).collect(Collectors.toList())),
					EnumAdaptor.valueOf(approvalRootContentExport.getErrorFlag().value, ErrorFlagImport.class));
	}

	@Override
	public void insertByAppType(String companyID, String employeeID, Integer appTypeValue, GeneralDate date,
			String historyID, String appID) {
		approvalRootStatePub.insertAppRootType(companyID, employeeID, appTypeValue, date, historyID, appID);
	}

	@Override
	public List<String> getNextApprovalPhaseStateMailList(String companyID, String rootStateID,
			Integer approvalPhaseStateNumber, Boolean isCreate, String employeeID, Integer appTypeValue,
			GeneralDate appDate) {
		return approvalRootStatePub.getNextApprovalPhaseStateMailList(companyID, rootStateID, approvalPhaseStateNumber, isCreate, employeeID, appTypeValue, appDate);
	}

	@Override
	public Integer doApprove(String companyID, String rootStateID, String employeeID, Boolean isCreate,
			Integer appTypeValue, GeneralDate appDate) {
		return approvalRootStatePub.doApprove(companyID, rootStateID, employeeID, isCreate, appTypeValue, appDate);
	}

	@Override
	public Boolean isApproveAllComplete(String companyID, String rootStateID, String employeeID, Boolean isCreate,
			Integer appTypeValue, GeneralDate appDate) {
		// TODO Auto-generated method stub
		return approvalRootStatePub.isApproveAllComplete(companyID, rootStateID, employeeID, isCreate, appTypeValue, appDate);
	}
	
}
