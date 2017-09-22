package nts.uk.file.com.app.company.approval;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.MasterApproverRootOutput;

public interface MasterApproverRootRepository {
	MasterApproverRootOutput getMasterInfo(String companyID,
			GeneralDate baseDate, 
			boolean isCompany, 
			boolean isWorkplace,
			boolean isPerson);

}
