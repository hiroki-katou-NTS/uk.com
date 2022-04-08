package nts.uk.ctx.workflow.pubimp.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.service.CollectApprovalRootService;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootContentOutput;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
import nts.uk.ctx.workflow.pub.service.CollectApprovalRootServicePub;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootServiceExport;
import nts.uk.ctx.workflow.pub.service.export.EmploymentRootAtrExport;
import nts.uk.ctx.workflow.pub.service.export.ErrorFlagExport;
import nts.uk.ctx.workflow.pub.service.export.RootTypeExport;
import nts.uk.ctx.workflow.pub.service.export.SystemAtrExport;

@Stateless
public class CollectApprovalRootServicePubImpl implements CollectApprovalRootServicePub {

	@Inject
	private CollectApprovalRootService collectApprovalRootService;

	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;

	@Override
	public ApprovalRootServiceExport createApprovalRootOfSubjectRequest(String companyID, String employeeID,
			EmploymentRootAtrExport rootAtr, String appType, GeneralDate standardDate, SystemAtrExport sysAtr,
			Optional<Boolean> lowerApprove, RootTypeExport rootType, String appId, GeneralDate appDate) {

		ApprovalRootContentOutput contentOutput = collectApprovalRootService.getApprovalRootOfSubjectRequest(companyID,
				employeeID, EnumAdaptor.valueOf(rootAtr.value, EmploymentRootAtr.class), appType, standardDate,
				EnumAdaptor.valueOf(sysAtr.value, SystemAtr.class), lowerApprove);

		if (contentOutput.getErrorFlag() != ErrorFlag.NO_ERROR) {
			return new ApprovalRootServiceExport(
					EnumAdaptor.valueOf(contentOutput.getErrorFlag().value, ErrorFlagExport.class), Optional.empty());
		}
		contentOutput.getApprovalRootState().setRootStateID(appId);
		contentOutput.getApprovalRootState().setEmployeeID(employeeID);
		contentOutput.getApprovalRootState().setApprovalRecordDate(appDate);
		AtomTask task = AtomTask.of(() -> {
			approvalRootStateRepository.insert(companyID, contentOutput.getApprovalRootState(), rootType.value);
		});
		return new ApprovalRootServiceExport(
				EnumAdaptor.valueOf(contentOutput.getErrorFlag().value, ErrorFlagExport.class), Optional.of(task));
	}
}
