package nts.uk.ctx.at.function.ac.monthly.agreement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.CheckAgreementTimeStatusAdapter;
import nts.uk.ctx.at.record.pub.monthly.agreement.CheckAgreementTimeStatusPub;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
@Stateless
public class CheckAgreementTimeStatusAcFinder implements CheckAgreementTimeStatusAdapter {

	@Inject
	private CheckAgreementTimeStatusPub checkAgreementTimeStatusPub;
	
	@Override
	public AgreMaxTimeStatusOfMonthly maxTime(AttendanceTimeMonth agreementTime, LimitOneMonth maxTime,
			Optional<AttendanceTimeMonth> requestTimeOpt) {
		return checkAgreementTimeStatusPub.maxTime(agreementTime, maxTime, requestTimeOpt);
	}

	@Override
	public AgreMaxAverageTimeMulti maxAverageTimeMulti(String companyId, AgreMaxAverageTimeMulti sourceTime,
			Optional<AttendanceTime> requestTimeOpt, Optional<GeneralDate> requestDateOpt) {
		return checkAgreementTimeStatusPub.maxAverageTimeMulti(companyId, sourceTime, requestTimeOpt, requestDateOpt);
	}

}
