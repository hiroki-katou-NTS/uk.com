package nts.uk.ctx.at.request.ac.record.agreement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.pub.monthlyprocess.agreement.GetAgreementTimePub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreeTimeYearImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeImport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreTimeYearStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Stateless
public class AgreementTimeAdapterImpl implements AgreementTimeAdapter {
	
	@Inject
	private GetAgreementTimePub getAgreementTimePub;
	
//	@Inject
//	private CheckAgreementTimeStatusPub checkAgreementTimeStatusPub;
	
//	@Inject
//	private GetAgreementPeriodPub getAgreementPeriodPub;
	
	@Override
	public List<AgreementTimeImport> getAgreementTime(String companyId, List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId) {
		
		/** TODO: 36協定時間対応により、コメントアウトされた */
		return new ArrayList<>();
//		return getAgreementTimePub.get(companyId, employeeIds, yearMonth, closureId).stream()
//			.map(x -> new AgreementTimeImport(
//					x.getEmployeeId(), 
//					x.getConfirmed().map(y -> new AgreeTimeOfMonthExport(
//							y.getAgreementTime().v(), 
//							y.getLimitErrorTime().v(), 
//							y.getLimitAlarmTime().v(), 
//							y.getExceptionLimitErrorTime().map(z -> z.v()), 
//							y.getExceptionLimitAlarmTime().map(z -> z.v()), 
//							y.getStatus().value)), 
//					x.getAfterAppReflect().map(y -> new AgreeTimeOfMonthExport(
//							y.getAgreementTime().v(), 
//							y.getLimitErrorTime().v(), 
//							y.getLimitAlarmTime().v(), 
//							y.getExceptionLimitErrorTime().map(z -> z.v()), 
//							y.getExceptionLimitAlarmTime().map(z -> z.v()), 
//							y.getStatus().value)),
//					x.getConfirmedMax().map(y -> new AgreMaxTimeOfMonthExport(
//							y.getAgreementTime().v(), 
//							y.getMaxTime().v(), 
//							y.getStatus().value)),
//					x.getAfterAppReflectMax().map(y -> new AgreMaxTimeOfMonthExport(
//							y.getAgreementTime().v(), 
//							y.getMaxTime().v(), 
//							y.getStatus().value)),
//					x.getErrorMessage()))
//			.collect(Collectors.toList());
	}

	@Override
	public Optional<AgreeTimeYearImport> getYear(String companyId, String employeeId, YearMonthPeriod period,
			GeneralDate criteria) {
		
		/** TODO: 36協定時間対応により、コメントアウトされた */
		return Optional.empty();
//		return getAgreementTimePub.getYear(companyId, employeeId, period, criteria)
//				.map(x -> new AgreeTimeYearImport(x.getLimitTime().v(), x.getRecordTime().v(), x.getStatus().value));
	}

	@Override
	public AgreTimeYearStatusOfMonthly timeYear(AgreementTimeYear agreementTimeYear, Optional<AttendanceTimeYear> requestTimeOpt) {
		
		/** TODO: 36協定時間対応により、コメントアウトされた */
		return null;
//		return checkAgreementTimeStatusPub.timeYear(agreementTimeYear, requestTimeOpt);
	}

	/** TODO: 36協定時間対応により、コメントアウトされた */
//	@Override
//	public AgreMaxTimeStatusOfMonthly maxTime(AttendanceTimeMonth agreementTime, AgreementOneMonthTime maxTime,
//			Optional<AttendanceTimeMonth> requestTimeOpt) {
//		
//		return checkAgreementTimeStatusPub.maxTime(agreementTime, maxTime, requestTimeOpt);
//	}

	@Override
	public AgreMaxAverageTimeMulti maxAverageTimeMulti(String companyId, AgreMaxAverageTimeMulti sourceTime,
			Optional<AttendanceTime> requestTimeOpt, Optional<GeneralDate> requestDateOpt) {
		
		/** TODO: 36協定時間対応により、コメントアウトされた */
		return null;
//		return checkAgreementTimeStatusPub.maxAverageTimeMulti(companyId, sourceTime, requestTimeOpt, requestDateOpt);
	}

	@Override
	public Optional<AgreMaxAverageTimeMulti> getMaxAverageMulti(String companyId, String employeeId,
			YearMonth yearMonth, GeneralDate criteria) {
		
		/** TODO: 36協定時間対応により、コメントアウトされた */
		return Optional.empty();
//		return getAgreementTimePub.getMaxAverageMulti(companyId, employeeId, yearMonth, criteria);
	}

	/** TODO: 36協定時間対応により、コメントアウトされた */
//	@Override
//	public Optional<YearMonthPeriod> containsDate(String companyID, GeneralDate criteria) {
//		return getAgreementPeriodPub.containsDate(companyID, criteria, Optional.empty());
//	}

	/** TODO: 36協定時間対応により、コメントアウトされた */
//	@Override
//	public AgreementTimeOutput getAverageAndYear(String companyId, String employeeId, YearMonth averageMonth,
//			GeneralDate criteria, ScheRecAtr scheRecAtr) {
//		return getAgreementTimePub.getAverageAndYear(companyId, employeeId, averageMonth, criteria, scheRecAtr);
//	}

}
