package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon;

import java.util.Optional;

public interface OvertimeRestAppCommonSetRepository {
	
	Optional<OvertimeRestAppCommonSetting> getOvertimeRestAppCommonSetting(String companyID);
}
