package nts.uk.ctx.at.function.dom.adapter.checkresultmonthly;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.AttendanceItemConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.AgreementCheckCon36FunImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.SpecHolidayCheckConFunImport;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;

public interface CheckResultMonthlyAdapter {
	
	boolean checkPublicHoliday(String companyId, String employeeCd , String employeeId,String workplaceId ,boolean isManageComPublicHd, YearMonth yearMonth,SpecHolidayCheckConFunImport specHolidayCheckCon );
	
	boolean check36AgreementCondition(String companyId,String employeeId,GeneralDate date,YearMonth yearMonth, Year year,AgreementCheckCon36FunImport agreementCheckCon36);
	
	boolean checkPerTimeMonActualResult(YearMonth yearMonth,int closureID, Integer closureDate,String employeeID,AttendanceItemConAdapterDto attendanceItemCondition);
}
