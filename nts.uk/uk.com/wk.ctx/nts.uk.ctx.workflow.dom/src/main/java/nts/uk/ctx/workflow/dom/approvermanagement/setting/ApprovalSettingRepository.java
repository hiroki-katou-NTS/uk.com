package nts.uk.ctx.workflow.dom.approvermanagement.setting;

import java.util.Optional;

public interface ApprovalSettingRepository {
	/**
	 * get principal by companyId
	 * @param companyId
	 * @return
	 */
	Optional<PrincipalApprovalFlg> getPrincipalByCompanyId(String companyId);
}
