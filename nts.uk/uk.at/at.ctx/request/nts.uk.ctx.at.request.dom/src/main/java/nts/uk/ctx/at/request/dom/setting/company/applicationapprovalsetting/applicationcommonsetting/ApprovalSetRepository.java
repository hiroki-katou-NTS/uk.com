package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting;

import java.util.Optional;

public interface ApprovalSetRepository {
	/**
	 * get approval set
	 * @return
	 * @author yennth
	 */
	Optional<ApprovalSet> getApproval();
	/**
	 * update approval set
	 * @param appro
	 * @author yennth
	 */
	void update(ApprovalSet appro);
	/**
	 * insert approval set
	 * @param appro
	 * @author yennth
	 */
	void insert(ApprovalSet appro);
}
