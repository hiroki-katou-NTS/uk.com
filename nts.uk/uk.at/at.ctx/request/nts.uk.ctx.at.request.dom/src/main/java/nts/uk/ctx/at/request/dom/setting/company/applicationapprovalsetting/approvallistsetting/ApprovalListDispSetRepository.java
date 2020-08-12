package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting;

import java.util.Optional;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface ApprovalListDispSetRepository {
	
	public Optional<ApprovalListDisplaySetting> findByCID(String companyID);
	
}
