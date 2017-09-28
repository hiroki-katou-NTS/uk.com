package nts.uk.ctx.workflow.dom.approvermanagement.setting;

public interface ApprovalSettingRepository {
	/**
	 * get principal by companyId
	 * @param companyId
	 * @return
	 */
	PrincipalApprovalFlg getPrincipalByCompanyId(String companyId);
}
