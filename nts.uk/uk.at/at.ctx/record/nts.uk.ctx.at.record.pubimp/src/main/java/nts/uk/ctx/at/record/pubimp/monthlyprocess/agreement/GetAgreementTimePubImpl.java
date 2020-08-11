package nts.uk.ctx.at.record.pubimp.monthlyprocess.agreement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.GetAgreementTime;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreMaxTimeOfMonthly;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.record.pub.monthlyprocess.agreement.AgreementTimeExport;
import nts.uk.ctx.at.record.pub.monthlyprocess.agreement.GetAgreementTimePub;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeOutput;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.ScheRecAtr;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * 実装：36協定時間の取得
 * @author shuichi_ishida
 */
@Stateless
public class GetAgreementTimePubImpl implements GetAgreementTimePub {

	@Inject 
	private RecordDomRequireService requireService;
	
	/** 36協定時間の取得 */
	@Override
	public List<AgreementTimeExport> get(String companyId, List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		
		List<AgreementTimeExport> result = new ArrayList<>();
		
		val agreementTimeList = GetAgreementTime.get(require, cacheCarrier, companyId, employeeIds, yearMonth, closureId);

		for (val agreementTime : agreementTimeList){
			val srcConfirmedOpt = agreementTime.getConfirmed();
			val srcAfterAppReflectOpt = agreementTime.getAfterAppReflect();
			val srcConfirmedMaxOpt = agreementTime.getConfirmedMax();
			val srcAfterAppReflectMaxOpt = agreementTime.getAfterAppReflectMax();
			
			// 確定情報
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
			
			// 申請反映後情報
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
			
			// 確定限度情報
			AgreMaxTimeOfMonthly confirmedMax = null;
			if (srcConfirmedMaxOpt.isPresent()){
				val srcConfirmedMax = srcConfirmedMaxOpt.get();
				confirmedMax = AgreMaxTimeOfMonthly.of(
						srcConfirmedMax.getAgreementTime(),
						new LimitOneMonth(srcConfirmedMax.getMaxTime().v()),
						srcConfirmedMax.getStatus());
			}
			
			// 申請反映後限度情報
			AgreMaxTimeOfMonthly afterAppReflectMax = null;
			if (srcAfterAppReflectMaxOpt.isPresent()){
				val srcAfterAppReflectMax = srcAfterAppReflectMaxOpt.get();
				afterAppReflectMax = AgreMaxTimeOfMonthly.of(
						srcAfterAppReflectMax.getAgreementTime(),
						new LimitOneMonth(srcAfterAppReflectMax.getMaxTime().v()),
						srcAfterAppReflectMax.getStatus());
			}
			
			String errorMessage = null;
			if (agreementTime.getErrorMessage().isPresent()) errorMessage = agreementTime.getErrorMessage().get();
			
			result.add(AgreementTimeExport.of(agreementTime.getEmployeeId(),
					confirmed, afterAppReflect, confirmedMax, afterAppReflectMax, errorMessage));
		}
		return result;
	}
	
	/** 36協定年間時間の取得 */
	@Override
	public Optional<AgreementTimeYear> getYear(String companyId, String employeeId, YearMonthPeriod period,
			GeneralDate criteria) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		
		return GetAgreementTime.getYear(require, cacheCarrier, companyId, employeeId, period, criteria);
	}
	
	/** 36協定上限複数月平均時間の取得 */
	@Override
	public Optional<AgreMaxAverageTimeMulti> getMaxAverageMulti(String companyId, String employeeId,
			YearMonth yearMonth, GeneralDate criteria) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		
		return GetAgreementTime.getMaxAverageMulti(require, cacheCarrier, companyId, employeeId, yearMonth, criteria);
	}
	
	/** 36協定上限複数月平均時間と年間時間の取得（日指定） */
	@Override
	public AgreementTimeOutput getAverageAndYear(String companyId, String employeeId, YearMonth averageMonth,
			GeneralDate criteria, ScheRecAtr scheRecAtr) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		return GetAgreementTime.getAverageAndYear(require, cacheCarrier, companyId, employeeId, averageMonth, criteria, scheRecAtr);
	}
	
	/** 36協定上限複数月平均時間と年間時間の取得（年度指定） */
	@Override
	public AgreementTimeOutput getAverageAndYear(String companyId, String employeeId, GeneralDate criteria,
			Year year, YearMonth averageMonth, ScheRecAtr scheRecAtr) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		return GetAgreementTime.getAverageAndYear(require, cacheCarrier, companyId, employeeId, criteria, year, averageMonth, scheRecAtr);
	}
}
