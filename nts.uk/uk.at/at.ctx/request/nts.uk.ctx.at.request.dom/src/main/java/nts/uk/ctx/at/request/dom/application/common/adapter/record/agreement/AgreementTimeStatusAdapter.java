package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.onemonth.AgreementOneMonth;

public interface AgreementTimeStatusAdapter {

	AgreementTimeStatusOfMonthly checkAgreementTimeStatus(
			AttendanceTimeMonth agreementTime,
			AgreementOneMonth limitAlarmTime,
			AgreementOneMonth limitErrorTime,
			Optional<AgreementOneMonth> exceptionLimitAlarmTime,
			Optional<AgreementOneMonth> exceptionLimitErrorTime);
}
