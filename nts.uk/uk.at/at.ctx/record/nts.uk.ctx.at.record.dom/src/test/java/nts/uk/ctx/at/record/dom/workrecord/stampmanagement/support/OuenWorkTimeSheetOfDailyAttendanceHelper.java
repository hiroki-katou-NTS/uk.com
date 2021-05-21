/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author laitv
 *
 */
public class OuenWorkTimeSheetOfDailyAttendanceHelper {
	
	public static OuenWorkTimeSheetOfDailyAttendance getDataTest1(){
		return OuenWorkTimeSheetOfDailyAttendance.create(
				1, 
				null, 
				TimeSheetOfAttendanceEachOuenSheet.create(
						new WorkNo(1), 
						Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(10))), 
						Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(60)))));
	}
	
	public static OuenWorkTimeSheetOfDailyAttendance getDataTest2(){
		return OuenWorkTimeSheetOfDailyAttendance.create(
				1, 
				null, 
				TimeSheetOfAttendanceEachOuenSheet.create(
						new WorkNo(1), 
						Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(20))), 
						Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(70)))));
	}
	
	public static OuenWorkTimeSheetOfDailyAttendance getDataTest3(){
		return OuenWorkTimeSheetOfDailyAttendance.create(
				1, 
				null, 
				TimeSheetOfAttendanceEachOuenSheet.create(
						new WorkNo(1), 
						Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(120))), 
						Optional.of(new WorkTimeInformation(ReasonTimeChange.createByAutomaticSet(), new TimeWithDayAttr(170)))));
	}
}
