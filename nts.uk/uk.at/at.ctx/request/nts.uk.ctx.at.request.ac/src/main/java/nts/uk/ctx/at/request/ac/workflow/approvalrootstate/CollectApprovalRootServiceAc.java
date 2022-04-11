package nts.uk.ctx.at.request.ac.workflow.approvalrootstate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.CollectApprovalRootServiceAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootServiceImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.EmploymentRootAtrImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.RootTypeImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.SystemAtrImport;
import nts.uk.ctx.workflow.pub.service.CollectApprovalRootServicePub;
import nts.uk.ctx.workflow.pub.service.export.EmploymentRootAtrExport;
import nts.uk.ctx.workflow.pub.service.export.RootTypeExport;
import nts.uk.ctx.workflow.pub.service.export.SystemAtrExport;

@Stateless
public class CollectApprovalRootServiceAc implements CollectApprovalRootServiceAdapter{

	@Inject
	private CollectApprovalRootServicePub pub;
	
	@Override
	public ApprovalRootServiceImport createApprovalRootOfSubjectRequest(String companyID, String employeeID,
			EmploymentRootAtrImport rootAtr, String appType, GeneralDate standardDate, SystemAtrImport sysAtr,
			Optional<Boolean> lowerApprove, RootTypeImport rootType, String appId, GeneralDate appDate) {
		val result =  pub.createApprovalRootOfSubjectRequest(companyID, 
				employeeID,
				EnumAdaptor.valueOf(rootAtr.value, EmploymentRootAtrExport.class), appType,
				standardDate,
				EnumAdaptor.valueOf(sysAtr.value, SystemAtrExport.class),
				lowerApprove,
				EnumAdaptor.valueOf(rootType.value, RootTypeExport.class),
				appId, appDate);
		return new ApprovalRootServiceImport(EnumAdaptor.valueOf(result.getErrorFlagExport().value, ErrorFlagImport.class), result.getTask());
	}

}
