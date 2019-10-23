package nts.uk.ctx.at.request.ac.record.agreement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.pub.monthly.agreement.CheckAgreementTimeStatusPub;
import nts.uk.ctx.at.record.pub.monthly.agreement.GetAgreementPeriodPub;
import nts.uk.ctx.at.record.pub.monthlyprocess.agreement.GetAgreementTimePub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreMaxTimeOfMonthExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreeTimeOfMonthExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreeTimeYearImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeImport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreTimeYearStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class AgreementTimeAdapterImpl implements AgreementTimeAdapter {
	
	@Inject
	private GetAgreementTimePub getAgreementTimePub;
	
	@Inject
	private CheckAgreementTimeStatusPub checkAgreementTimeStatusPub;
	
	@Inject
	private GetAgreementPeriodPub getAgreementPeriodPub;
	
	@Override
	public List<AgreementTimeImport> getAgreementTime(String companyId, List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId) {
		return getAgreementTimePub.get(companyId, employeeIds, yearMonth, closureId).stream()
			.map(x -> new AgreementTimeImport(
					x.getEmployeeId(), 
					x.getConfirmed().map(y -> new AgreeTimeOfMonthExport(
							y.getAgreementTime().v(), 
							y.getLimitErrorTime().v(), 
							y.getLimitAlarmTime().v(), 
							y.getExceptionLimitErrorTime().map(z -> z.v()), 
							y.getExceptionLimitAlarmTime().map(z -> z.v()), 
							y.getStatus().value)), 
					x.getAfterAppReflect().map(y -> new AgreeTimeOfMonthExport(
							y.getAgreementTime().v(), 
							y.getLimitErrorTime().v(), 
							y.getLimitAlarmTime().v(), 
							y.getExceptionLimitErrorTime().map(z -> z.v()), 
							y.getExceptionLimitAlarmTime().map(z -> z.v()), 
							y.getStatus().value)),
					x.getConfirmedMax().map(y -> new AgreMaxTimeOfMonthExport(
							y.getAgreementTime().v(), 
							y.getMaxTime().v(), 
							y.getStatus().value)),
					x.getAfterAppReflectMax().map(y -> new AgreMaxTimeOfMonthExport(
							y.getAgreementTime().v(), 
							y.getMaxTime().v(), 
							y.getStatus().value)),
					x.getErrorMessage()))
			.collect(Collectors.toList());
	}

	@Override
	public Optional<AgreeTimeYearImport> getYear(String companyId, String employeeId, YearMonthPeriod period,
			GeneralDate criteria) {
		return getAgreementTimePub.getYear(companyId, employeeId, period, criteria)
				.map(x -> new AgreeTimeYearImport(x.getLimitTime().v(), x.getRecordTime().v(), x.getStatus().value));
	}

	@Override
	public AgreTimeYearStatusOfMonthly timeYear(AgreementTimeYear agreementTimeYear, Optional<AttendanceTimeYear> requestTimeOpt) {
		return checkAgreementTimeStatusPub.timeYear(agreementTimeYear, requestTimeOpt);
	}

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

	@Override
	public Optional<AgreMaxAverageTimeMulti> getMaxAverageMulti(String companyId, String employeeId,
			YearMonth yearMonth, GeneralDate criteria) {
		return getAgreementTimePub.getMaxAverageMulti(companyId, employeeId, yearMonth, criteria);
	}

	@Override
	public Optional<YearMonthPeriod> containsDate(String companyID, GeneralDate criteria) {
		return getAgreementPeriodPub.containsDate(companyID, criteria, Optional.empty());
	}

}
