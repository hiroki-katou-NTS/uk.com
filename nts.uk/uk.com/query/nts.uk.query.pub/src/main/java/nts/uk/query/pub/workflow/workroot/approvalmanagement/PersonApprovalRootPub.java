package nts.uk.query.pub.workflow.workroot.approvalmanagement;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface PersonApprovalRootPub {

	List<ApprovalSettingInformationExport> findApproverList(String cid, List<String> sids, GeneralDate baseDate,
			int systemAtr);
}
