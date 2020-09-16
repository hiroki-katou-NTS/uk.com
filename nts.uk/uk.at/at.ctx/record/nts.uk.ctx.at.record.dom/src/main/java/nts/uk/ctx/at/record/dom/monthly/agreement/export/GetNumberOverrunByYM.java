package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.setting.AgreementOperationSetting;

public interface GetNumberOverrunByYM {
	Optional<AgreementExcessInfo> getNumberOverrunByYearMonth(String employeeId, YearMonth yearMonth, Optional<AgreementOperationSetting> agreeOpSetOpt);
}
