package nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo;

import java.math.BigDecimal;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public class EmpLeaveWorkPeriodImportHelper {
	
	public static EmpLeaveWorkPeriodImport getData(){
		
		return new EmpLeaveWorkPeriodImport(
				"empID-000001",
				new TempAbsenceFrameNo(new BigDecimal(1)),
				new DatePeriod(GeneralDate.today(), GeneralDate.today()));
	}
	

}
