package nts.uk.ctx.at.request.ac.record.agreement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.monthly.agreement.CheckAgreementTimeStatusPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeStatusAdapter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.onemonth.AgreementOneMonth;

@Stateless
public class AgreementTimeStatusAdapterAcFinder implements AgreementTimeStatusAdapter {
	@Inject
	private CheckAgreementTimeStatusPub checkAgreementTimeStatusPub;

	@Override
	public AgreementTimeStatusOfMonthly checkAgreementTimeStatus(AttendanceTimeMonth agreementTime,
			AgreementOneMonth limitAlarmTime, AgreementOneMonth limitErrorTime, Optional<AgreementOneMonth> exceptionLimitAlarmTime,
			Optional<AgreementOneMonth> exceptionLimitErrorTime) {
		return checkAgreementTimeStatusPub.algorithm(agreementTime, limitAlarmTime, limitErrorTime,
				exceptionLimitAlarmTime, exceptionLimitErrorTime);
	}

}
