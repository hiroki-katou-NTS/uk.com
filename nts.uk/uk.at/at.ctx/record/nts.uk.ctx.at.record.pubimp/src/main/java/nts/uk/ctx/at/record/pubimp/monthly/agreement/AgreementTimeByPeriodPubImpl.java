package nts.uk.ctx.at.record.pubimp.monthly.agreement;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgeementTimeCommonSetting;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgeementTimeCommonSettingService;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreTimeByPeriod;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreMaxTimeMonthOut;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreMaxTimeOfMonthly;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeByEmpExport;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeByPeriod;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeByPeriodPub;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.PeriodAtrOfAgreement;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * 指定期間36協定時間の取得
 * @author shuichi_ishida
 */
@Stateless
public class AgreementTimeByPeriodPubImpl implements AgreementTimeByPeriodPub {

	/** 指定期間36協定時間の取得 */
	@Inject
	private GetAgreTimeByPeriod getAgreTimeByPeriod;
	
	@Inject
	public AgeementTimeCommonSettingService settingService;
	
	/** 指定期間36協定時間の取得 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<AgreementTimeByPeriod> algorithm(String companyId, String employeeId, GeneralDate criteria,
			Month startMonth, Year year, PeriodAtrOfAgreement periodAtr) {

		return this.getAgreTimeByPeriod.algorithm(companyId, employeeId, criteria, startMonth, year, periodAtr)
				.stream().map(c -> toPub(c)).collect(Collectors.toList());
	}

	private AgreementTimeByPeriod toPub(
			nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementTimeByPeriod domain){
		
		return AgreementTimeByPeriod.of(
				domain.getStartMonth(),
				domain.getEndMonth(),
				domain.getAgreementTime(),
				domain.getLimitErrorTime(),
				domain.getLimitAlarmTime(),
				domain.getExceptionLimitErrorTime(),
				domain.getExceptionLimitAlarmTime(),
				domain.getStatus());
	}

	@Override
	public List<AgreementTimeByEmpExport> algorithmImprove(String companyId, List<String> employeeIds, GeneralDate criteria,
													Month startMonth, Year year, List<PeriodAtrOfAgreement> periodAtrs, Map<String, YearMonthPeriod> periodWorking) {
		return this.getAgreTimeByPeriod.algorithmImprove(companyId, employeeIds, criteria, startMonth, year, periodAtrs, periodWorking)
				.stream().map(AgreementTimeByEmpExport::fromDomain).collect(Collectors.toList());
	}
	
	/** 指定月36協定上限月間時間の取得 */
	@Override
	public List<AgreMaxTimeMonthOut> maxTime(String companyId, String employeeId, YearMonthPeriod period) {

		return this.getAgreTimeByPeriod.maxTime(companyId, employeeId, period)
				.stream().map(c -> toPub(c)).collect(Collectors.toList());
	}
	
	private AgreMaxTimeMonthOut toPub(
			nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreMaxTimeMonthOut domain){
		
		return AgreMaxTimeMonthOut.of(
				domain.getYearMonth(),
				AgreMaxTimeOfMonthly.of(
						domain.getMaxTime().getAgreementTime(),
						new LimitOneMonth(domain.getMaxTime().getMaxTime().v()),
						domain.getMaxTime().getStatus()));
	}
	
	/** 指定期間36協定上限複数月平均時間の取得 */
	@Override
	public Optional<AgreMaxAverageTimeMulti> maxAverageTimeMulti(String companyId, String employeeId, GeneralDate criteria,
			YearMonth yearMonth) {
		
		return this.getAgreTimeByPeriod.maxAverageTimeMulti(companyId, employeeId, criteria, yearMonth);
	}
	
	/** 指定年36協定年間時間の取得 */
	@Override
	public Optional<AgreementTimeYear> timeYear(String companyId, String employeeId, GeneralDate criteria, Year year) {

		return this.getAgreTimeByPeriod.timeYear(companyId, employeeId, criteria, year);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<AgreementTimeByPeriod> algorithm(String companyId, String employeeId, GeneralDate criteria,
			Month startMonth, Year year, PeriodAtrOfAgreement periodAtr, Object basicSetGetter) {
		
		return this.getAgreTimeByPeriod.algorithm(companyId, employeeId, criteria, startMonth, year, periodAtr, (AgeementTimeCommonSetting) basicSetGetter)
				.stream().map(c -> toPub(c)).collect(Collectors.toList());
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Object getCommonSetting(String companyId, List<String> employeeIds, DatePeriod criteria) {
		
		return this.settingService.getCommonService(companyId, employeeIds, criteria);
	}
}
