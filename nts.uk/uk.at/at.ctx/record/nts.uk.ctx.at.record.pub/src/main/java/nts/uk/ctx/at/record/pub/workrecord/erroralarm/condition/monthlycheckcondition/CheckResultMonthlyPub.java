package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.AttendanceItemConditionPubExport;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;

public interface CheckResultMonthlyPub {
	/**
	 *  公休所定日数をチェックする
	 * @param companyId
	 * @param employeeCd
	 * @param employeeId
	 * @param workplaceId
	 * @param isManageComPublicHd
	 * @param yearMonth
	 * @param specHolidayCheckCon
	 * @return
	 */
	boolean checkPublicHoliday(String companyId, String employeeCd , String employeeId,String workplaceId ,boolean isManageComPublicHd, YearMonth yearMonth,SpecHolidayCheckConPubEx specHolidayCheckCon );
	
	/**
	 * 36協定チェック条件をチェックする 
	 * @param companyId
	 * @param employeeId
	 * @param date
	 * @param yearMonth
	 * @param year
	 * @param agreementCheckCon36
	 * @return
	 */
	boolean check36AgreementCondition(String companyId,String employeeId,GeneralDate date,YearMonth yearMonth, Year year,AgreementCheckCon36PubEx agreementCheckCon36);
	
	/**
	 * 月別実績をチェックする 
	 * @param yearMonth
	 * @param closureID
	 * @param closureDate
	 * @param employeeID
	 * @param attendanceItemCondition
	 * @return
	 */
	boolean checkPerTimeMonActualResult(YearMonth yearMonth,int closureID, Integer closureDate,String employeeID,AttendanceItemConditionPubExport attendanceItemCondition);
}
