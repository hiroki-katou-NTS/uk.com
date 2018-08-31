package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon;

import java.util.Optional;

public interface OvertimeRestAppCommonSetRepository {
	
	/**
	 * @param companyID
	 * @return
	 */
	Optional<OvertimeRestAppCommonSetting> getOvertimeRestAppCommonSetting(String companyID, int appType);
	
	void update(OvertimeRestAppCommonSetting otRestAppCommonSet);
	
	void insert(OvertimeRestAppCommonSetting domain);
}
