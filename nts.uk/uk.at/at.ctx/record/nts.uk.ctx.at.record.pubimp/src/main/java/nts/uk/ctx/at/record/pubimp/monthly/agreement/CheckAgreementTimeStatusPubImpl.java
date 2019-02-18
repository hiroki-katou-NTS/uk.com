package nts.uk.ctx.at.record.pubimp.monthly.agreement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.CheckAgreementTimeStatus;
import nts.uk.ctx.at.record.pub.monthly.agreement.CheckAgreementTimeStatusPub;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreTimeYearStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;

/**
 * 実装：36協定時間の状態チェック
 * @author shuichi_ishida
 */
@Stateless
public class CheckAgreementTimeStatusPubImpl implements CheckAgreementTimeStatusPub {

	/** 36協定時間の状態チェック */
	@Inject
	private CheckAgreementTimeStatus checkAgreementTimeStatus;
	
	/** 36協定時間の状態チェック */
	@Override
	public AgreementTimeStatusOfMonthly algorithm(AttendanceTimeMonth agreementTime, LimitOneMonth limitAlarmTime,
			LimitOneMonth limitErrorTime, Optional<LimitOneMonth> exceptionLimitAlarmTime,
			Optional<LimitOneMonth> exceptionLimitErrorTime) {
		
		return this.checkAgreementTimeStatus.algorithm(
				agreementTime, limitAlarmTime, limitErrorTime, exceptionLimitAlarmTime, exceptionLimitErrorTime);
	}
	
	/** 36協定上限時間の状態チェック */
	@Override
	public AgreMaxTimeStatusOfMonthly maxTime(AttendanceTimeMonth agreementTime, LimitOneMonth maxTime,
			Optional<AttendanceTimeMonth> requestTimeOpt) {
		
		return this.checkAgreementTimeStatus.maxTime(agreementTime, maxTime, requestTimeOpt);
	}
	
	/** 36協定上限複数月平均時間の状態チェック */
	@Override
	public AgreMaxAverageTimeMulti maxAverageTimeMulti(String companyId, AgreMaxAverageTimeMulti sourceTime,
			Optional<AttendanceTime> requestTimeOpt, Optional<GeneralDate> requestDateOpt) {

		return this.maxAverageTimeMulti(companyId, sourceTime, requestTimeOpt, requestDateOpt);
	}
	
	/** 36協定年間時間の状態チェック */
	@Override
	public AgreTimeYearStatusOfMonthly timeYear(AgreementTimeYear agreementTimeYear,
			Optional<AttendanceTimeYear> requestTimeOpt) {
		
		return this.timeYear(agreementTimeYear, requestTimeOpt);
	}
}
