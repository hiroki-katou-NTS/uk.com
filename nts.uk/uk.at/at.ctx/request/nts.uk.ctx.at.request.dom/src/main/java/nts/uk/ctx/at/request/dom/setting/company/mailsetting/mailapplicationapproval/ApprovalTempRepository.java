package nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailapplicationapproval;

import java.util.Optional;
/**
 * the approval template repository interface
 * @author yennth
 *
 */
public interface ApprovalTempRepository {
	/**
	 * get approval template by company Id
	 * @return
	 * @author yennth
	 */
	Optional<ApprovalTemp> getAppTem();
	/**
	 * update approval template
	 * @param appTemp
	 * @author yennth
	 */
	void update(ApprovalTemp appTemp);
	/**
	 * insert approval template
	 * @param appTemp
	 * @author yennth
	 */
	void insert(ApprovalTemp appTemp);
}
