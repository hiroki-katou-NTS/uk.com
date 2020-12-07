package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import nts.arc.time.YearMonth;

public interface CheckingPublicHolidayService {
	// 公休所定日数をチェックする
	boolean checkPublicHoliday(String companyId, String employeeCd , String employeeId,String workplaceId ,int isManageComPublicHd, YearMonth yearMonth,SpecHolidayCheckCon specHolidayCheckCon );
}
