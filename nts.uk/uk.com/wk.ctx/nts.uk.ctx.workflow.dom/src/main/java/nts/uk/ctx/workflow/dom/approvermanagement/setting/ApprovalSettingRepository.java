package nts.uk.ctx.workflow.dom.approvermanagement.setting;

import java.util.Optional;

public interface ApprovalSettingRepository {
	/**
	 * get principal by companyId
	 * @param companyId
	 * @return
	 */
	Optional<PrincipalApprovalFlg> getPrincipalByCompanyId(String companyId);
	/**
	 * update approval setting
	 * @param appro
	 * @author yennth
	 */
	void update(ApprovalSetting appro);
	/**
	 * insert approval setting
	 * @param appro
	 * @author yennth
	 */
	void insert(ApprovalSetting appro);
	/**
	 * get approval setting by companyId
	 * @param companyId
	 * @return
	 * @author yennth
	 */
	Optional<ApprovalSetting> getApprovalByComId(String companyId);
	
	void updateForUnit(ApprovalSetting appro);
}
