package nts.uk.ctx.at.function.dom.adapter.checkresultmonthly;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.AttendanceItemConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.AgreementCheckCon36FunImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.SpecHolidayCheckConFunImport;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;

public interface CheckResultMonthlyAdapter {
	
	boolean checkPublicHoliday(String companyId, String employeeCd , String employeeId,String workplaceId ,boolean isManageComPublicHd, YearMonth yearMonth,SpecHolidayCheckConFunImport specHolidayCheckCon );
	
	Check36AgreementValueImport check36AgreementCondition(String employeeId,YearMonth yearMonth,int closureID,ClosureDate closureDate,AgreementCheckCon36FunImport agreementCheckCon36);
	
	boolean checkPerTimeMonActualResult(YearMonth yearMonth,int closureID, ClosureDate closureDate,String employeeID,AttendanceItemConAdapterDto attendanceItemCondition);
}
