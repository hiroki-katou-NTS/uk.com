package nts.uk.ctx.at.record.pubimp.monthly.agreement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeBreakdown;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeOfManagePeriodPub;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：管理期間の36協定時間の取得
 * @author shuichu_ishida
 */
@Stateless
public class AgreementTimeOfManagePeriodPubImpl implements AgreementTimeOfManagePeriodPub {

	/** 管理期間の36協定時間 */
	@Inject
	private AgreementTimeOfManagePeriodRepository agreementTimeRepo;
	
	@Override
	public Optional<AgreementTimeOfManagePeriod> find(String employeeId, YearMonth yearMonth) {
		
		val srcAgreementTimeOpt = this.agreementTimeRepo.find(employeeId, yearMonth);
		if (!srcAgreementTimeOpt.isPresent()) return Optional.empty();
		return Optional.of(this.toPubDomain(srcAgreementTimeOpt.get()));
	}
	
	@Override
	public List<AgreementTimeOfManagePeriod> findByRange(String employeeId, DatePeriod range) {

		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(employeeId);
		
		List<YearMonth> ymRange = new ArrayList<>();
		YearMonth startYm = YearMonth.of(range.start().year(), range.start().month());
		YearMonth endYm = YearMonth.of(range.end().year(), range.end().month());
		while (startYm.lessThanOrEqualTo(endYm)){
			ymRange.add(startYm);
			startYm = startYm.addMonths(1);
		}
		
		val srcAgreementTimeList = this.agreementTimeRepo.findBySidsAndYearMonths(employeeIds, ymRange);
		return srcAgreementTimeList.stream().map(c -> this.toPubDomain(c)).collect(Collectors.toList());
	}
	
	private AgreementTimeOfManagePeriod toPubDomain(
			nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod fromDomain){
		
		val fromAgreementTime = fromDomain.getAgreementTime();
		LimitOneMonth fromLimitErrorTime = null;
		if (fromAgreementTime.getExceptionLimitErrorTime().isPresent()){
			fromLimitErrorTime = new LimitOneMonth(fromAgreementTime.getExceptionLimitErrorTime().get().v());
		}
		LimitOneMonth fromLimitAlarmTime = null;
		if (fromAgreementTime.getExceptionLimitAlarmTime().isPresent()){
			fromLimitAlarmTime = new LimitOneMonth(fromAgreementTime.getExceptionLimitAlarmTime().get().v());
		}
		val fromBreakdown = fromDomain.getBreakdown();
		
		return AgreementTimeOfManagePeriod.of(
				fromDomain.getEmployeeId(),
				fromDomain.getYearMonth(),
				fromDomain.getYear(),
				AgreementTimeOfMonthly.of(
						fromAgreementTime.getAgreementTime(),
						new LimitOneMonth(fromAgreementTime.getLimitErrorTime().v()),
						new LimitOneMonth(fromAgreementTime.getLimitAlarmTime().v()),
						Optional.ofNullable(fromLimitErrorTime),
						Optional.ofNullable(fromLimitAlarmTime),
						fromAgreementTime.getStatus()),
				AgreementTimeBreakdown.of(
						fromBreakdown.getOverTime(),
						fromBreakdown.getTransferOverTime(),
						fromBreakdown.getHolidayWorkTime(),
						fromBreakdown.getTransferTime(),
						fromBreakdown.getFlexExcessTime(),
						fromBreakdown.getWithinPrescribedPremiumTime(),
						fromBreakdown.getWeeklyPremiumTime(),
						fromBreakdown.getMonthlyPremiumTime()));
	}
}
