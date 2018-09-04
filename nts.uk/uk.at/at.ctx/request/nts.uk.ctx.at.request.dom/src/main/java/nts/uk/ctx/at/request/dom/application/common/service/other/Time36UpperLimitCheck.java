package nts.uk.ctx.at.request.dom.application.common.service.other;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AppTimeItem;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.Time36UpperLimitCheckResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;

public interface Time36UpperLimitCheck {
	/**
	 * 18.３６時間の上限チェック(新規登録)
	 */
	Time36UpperLimitCheckResult checkRegister(String companyId, String employeeId, GeneralDate appDate,
			ApplicationType appType, List<AppTimeItem> appTimeItems);

	/**
	 * 19.３６時間の上限チェック(照会)
	 */
	Time36UpperLimitCheckResult checkUpdate(String companyId, Optional<AppOvertimeDetail> appOvertimeDetailOpt,
			String employeeId, ApplicationType appType, List<AppTimeItem> appTimeItems);
}
