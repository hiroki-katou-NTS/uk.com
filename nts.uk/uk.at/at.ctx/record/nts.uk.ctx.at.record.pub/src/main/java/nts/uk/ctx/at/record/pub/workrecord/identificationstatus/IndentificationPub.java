package nts.uk.ctx.at.record.pub.workrecord.identificationstatus;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * @author thanhnx
 *
 */
public interface IndentificationPub {
	
	/**
	 * RequestList #165
	 * @param employeeId
	 * @param datePeriod
	 * @return List<GeneralDate> List<年月日>
	 */
    List<GeneralDate> getResovleDateIdentify(String employeeId, DatePeriod datePeriod);
}
