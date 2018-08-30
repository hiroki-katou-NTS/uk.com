package nts.uk.ctx.at.record.pubimp.monthly.agreement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthly.agreement.export.CheckAgreementTimeStatus;
import nts.uk.ctx.at.record.pub.monthly.agreement.CheckAgreementTimeStatusPub;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
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
}
