package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreTimeYearStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeOutput;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.ScheRecAtr;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.arc.time.calendar.period.YearMonthPeriod;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AgreementTimeAdapter {
	
	public List<AgreementTimeImport> getAgreementTime(String companyId, List<String> employeeIds, YearMonth yearMonth, ClosureId closureId);
	
	public Optional<AgreeTimeYearImport> getYear(String companyId, String employeeId, YearMonthPeriod period, GeneralDate criteria);
	
	public AgreTimeYearStatusOfMonthly timeYear(AgreementTimeYear agreementTimeYear, Optional<AttendanceTimeYear> requestTimeOpt);
	
	public AgreMaxTimeStatusOfMonthly maxTime(AttendanceTimeMonth agreementTime, LimitOneMonth maxTime, Optional<AttendanceTimeMonth> requestTimeOpt);
	
	public AgreMaxAverageTimeMulti maxAverageTimeMulti(
			String companyId,
			AgreMaxAverageTimeMulti sourceTime,
			Optional<AttendanceTime> requestTimeOpt,
			Optional<GeneralDate> requestDateOpt);
	
	public Optional<AgreMaxAverageTimeMulti> getMaxAverageMulti(String companyId, String employeeId, YearMonth yearMonth, GeneralDate criteria);
	
//	public Optional<YearMonthPeriod> containsDate(String companyID, GeneralDate criteria);
	
	public AgreementTimeOutput getAverageAndYear(String companyId, String employeeId, YearMonth averageMonth,
			GeneralDate criteria, ScheRecAtr scheRecAtr);
	
}
