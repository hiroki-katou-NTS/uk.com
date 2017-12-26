package nts.uk.ctx.at.request.ac.workflow.approvalroot;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EnumType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.CollectApprovalRootContentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
import nts.uk.ctx.workflow.pub.service.export.ApplicationTypeExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalBehaviorAtrExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootContentExport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CollectApprovalRootContentImpl implements CollectApprovalRootContentAdapter {

	@Inject
	private ApprovalRootStatePub approvalRootStatePub;
	
	@Override
	public ApprovalRootContentImport_New getApprovalRootContent(String companyID, String employeeID, Integer appTypeValue, GeneralDate appDate, String appID) {
		ApprovalRootContentExport approvalRootContentExport = approvalRootStatePub.getApprovalRoot(companyID, employeeID, appTypeValue, appDate, appID);
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

}
