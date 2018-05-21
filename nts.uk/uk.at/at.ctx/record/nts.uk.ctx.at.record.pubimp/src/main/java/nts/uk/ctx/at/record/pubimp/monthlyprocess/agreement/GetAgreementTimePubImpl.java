package nts.uk.ctx.at.record.pubimp.monthlyprocess.agreement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.GetAgreementTime;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.record.pub.monthlyprocess.agreement.AgreementTimeExport;
import nts.uk.ctx.at.record.pub.monthlyprocess.agreement.GetAgreementTimePub;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 実装：36協定時間の取得
 * @author shuichu_ishida
 */
@Stateless
public class GetAgreementTimePubImpl implements GetAgreementTimePub {

	/** 36協定時間の取得 */
	@Inject
	private GetAgreementTime getAgreementTime;
	
	@Override
	public List<AgreementTimeExport> get(String companyId, List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId) {
		
		List<AgreementTimeExport> result = new ArrayList<>();
		
		val agreementTimeList = this.getAgreementTime.get(companyId, employeeIds, yearMonth, closureId);

		for (val agreementTime : agreementTimeList){
			val srcConfirmedOpt = agreementTime.getConfirmed();
			val srcAfterAppReflectOpt = agreementTime.getAfterAppReflect();
			
			AgreementTimeOfMonthly confirmed = null;
			if (srcConfirmedOpt.isPresent()){
				val srcConfirmed = srcConfirmedOpt.get();
				LimitOneMonth srcCnfExcLimitErrorTime = null;
				if (srcConfirmed.getExceptionLimitErrorTime().isPresent()){
					srcCnfExcLimitErrorTime = new LimitOneMonth(srcConfirmed.getExceptionLimitErrorTime().get().v());
				}
				LimitOneMonth srcCnfExcLimitAlarmTime = null;
				if (srcConfirmed.getExceptionLimitAlarmTime().isPresent()){
					srcCnfExcLimitAlarmTime = new LimitOneMonth(srcConfirmed.getExceptionLimitAlarmTime().get().v());
				}
				confirmed = AgreementTimeOfMonthly.of(
						srcConfirmed.getAgreementTime(),
						new LimitOneMonth(srcConfirmed.getLimitErrorTime().v()),
						new LimitOneMonth(srcConfirmed.getLimitAlarmTime().v()),
						Optional.ofNullable(srcCnfExcLimitErrorTime),
						Optional.ofNullable(srcCnfExcLimitAlarmTime),
						srcConfirmed.getStatus());
			}
			
			AgreementTimeOfMonthly afterAppReflect = null;
			if (srcAfterAppReflectOpt.isPresent()){
				val srcAfterAppReflect = srcAfterAppReflectOpt.get();
				LimitOneMonth srcAppExcLimitErrorTime = null;
				if (srcAfterAppReflect.getExceptionLimitErrorTime().isPresent()){
					srcAppExcLimitErrorTime = new LimitOneMonth(srcAfterAppReflect.getExceptionLimitErrorTime().get().v());
				}
				LimitOneMonth srcAppExcLimitAlarmTime = null;
				if (srcAfterAppReflect.getExceptionLimitAlarmTime().isPresent()){
					srcAppExcLimitAlarmTime = new LimitOneMonth(srcAfterAppReflect.getExceptionLimitAlarmTime().get().v());
				}
				afterAppReflect = AgreementTimeOfMonthly.of(
						srcAfterAppReflect.getAgreementTime(),
						new LimitOneMonth(srcAfterAppReflect.getLimitErrorTime().v()),
						new LimitOneMonth(srcAfterAppReflect.getLimitAlarmTime().v()),
						Optional.ofNullable(srcAppExcLimitErrorTime),
						Optional.ofNullable(srcAppExcLimitAlarmTime),
						srcAfterAppReflect.getStatus());
			}
			
			String errorMessage = null;
			if (agreementTime.getErrorMessage().isPresent()) errorMessage = agreementTime.getErrorMessage().get();
			
			result.add(AgreementTimeExport.of(agreementTime.getEmployeeId(),
					confirmed, afterAppReflect, errorMessage));
		}
		return result;
	}
}
