/**
 * 
 */
package nts.uk.ctx.at.record.dom.daily.ouen;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.premiumitem.PriceUnit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime.PremiumTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.MedicalCareTimeEachTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenAttendanceTimeEachTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenMovementTimeEachTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;

/**
 * @author laitv
 *
 */
public class OuenWorkTimeOfDailyHelper {
	
	public static OuenWorkTimeOfDaily getOuenWorkTimeOfDailyDefault() {
		List<OuenWorkTimeOfDailyAttendance> ouenTimes = new ArrayList<OuenWorkTimeOfDailyAttendance>();
		
		OuenWorkTimeOfDailyAttendance ouenTime = OuenWorkTimeOfDailyAttendance.create(
				1, 
				OuenAttendanceTimeEachTimeSheet.create(
						new AttendanceTime(10), 
						new AttendanceTime(12), 
						new AttendanceTime(14), 
						new ArrayList<MedicalCareTimeEachTimeSheet>(), 
						new ArrayList<PremiumTime>()), 
				OuenMovementTimeEachTimeSheet.create(
						new AttendanceTime(10), 
						new AttendanceTime(12), 
						new AttendanceTime(14), 
						new ArrayList<PremiumTime>()), 
				new AttendanceAmountDaily(100), 
				new PriceUnit(200));
		ouenTimes.add(ouenTime);
		return OuenWorkTimeOfDaily.create(
				"empId", 
				GeneralDate.today(), 
				ouenTimes);
	}
}
