package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreTimeYearStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AgreementTimeAdapter {
	
	public List<AgreementTimeImport> getAgreementTime(String companyId, List<String> employeeIds, YearMonth yearMonth, ClosureId closureId);
	
	public Optional<AgreeTimeYearImport> getYear(String companyId, String employeeId, YearMonthPeriod period, GeneralDate criteria);
	
	public AgreTimeYearStatusOfMonthly timeYear(AgreementTimeYear agreementTimeYear, Optional<AttendanceTimeYear> requestTimeOpt);
	
	/** TODO: 36協定時間対応により、コメントアウトされた */
//	public AgreMaxTimeStatusOfMonthly maxTime(AttendanceTimeMonth agreementTime, AgreementOneMonth maxTime, Optional<AttendanceTimeMonth> requestTimeOpt);
	
	public AgreMaxAverageTimeMulti maxAverageTimeMulti(
			String companyId,
			AgreMaxAverageTimeMulti sourceTime,
			Optional<AttendanceTime> requestTimeOpt,
			Optional<GeneralDate> requestDateOpt);
	
	public Optional<AgreMaxAverageTimeMulti> getMaxAverageMulti(String companyId, String employeeId, YearMonth yearMonth, GeneralDate criteria);
	
//	public Optional<YearMonthPeriod> containsDate(String companyID, GeneralDate criteria);
	
	/** TODO: 36協定時間対応により、コメントアウトされた */
//	public AgreementTimeOutput getAverageAndYear(String companyId, String employeeId, YearMonth averageMonth,
//			GeneralDate criteria, ScheRecAtr scheRecAtr);
	
}
