package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;

public interface AgreementTimeStatusAdapter {

	AgreementTimeStatusOfMonthly checkAgreementTimeStatus(
			AttendanceTimeMonth agreementTime,
			LimitOneMonth limitAlarmTime,
			LimitOneMonth limitErrorTime,
			Optional<LimitOneMonth> exceptionLimitAlarmTime,
			Optional<LimitOneMonth> exceptionLimitErrorTime);
}
