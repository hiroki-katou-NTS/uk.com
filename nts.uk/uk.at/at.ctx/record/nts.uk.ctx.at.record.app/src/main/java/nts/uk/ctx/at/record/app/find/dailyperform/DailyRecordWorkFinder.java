package nts.uk.ctx.at.record.app.find.dailyperform;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.AffiliationInforOfDailyPerforFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.AttendanceLeavingGateOfDailyFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.CalcAttrOfDailyPerformanceFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.erroralarm.EmployeeDailyPerErrorFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.goout.OutingTimeOfDailyPerformanceFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.OptionalItemOfDailyPerformFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.RestTimeZoneOfDailyFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.ShortTimeOfDailyFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.SpecificDateAttrOfDailyPerforFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.TemporaryTimeOfDailyPerformanceFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.WorkInformationOfDailyFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.AttendanceTimeByWorkOfDailyFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.TimeLeavingOfDailyPerformanceFinder;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class DailyRecordWorkFinder extends FinderFacade {

	@Inject
	private AttendanceTimeOfDailyPerformFinder attendanceItemFinder;
	@Inject
	private AffiliationInforOfDailyPerforFinder affiliationInfoFinder;
	@Inject
	private AttendanceLeavingGateOfDailyFinder attendanceLeavingGateFinder;
	@Inject
	private OutingTimeOfDailyPerformanceFinder outingTimeFinder;
	@Inject
	private OptionalItemOfDailyPerformFinder optionalItemFinder;
	// @Inject
	// private PCLogOnInforOfDailyPerformFinder pcLogOnInfoFinder;
	@Inject
	private RestTimeZoneOfDailyFinder breakItemFinder;
	@Inject
	private SpecificDateAttrOfDailyPerforFinder specificDateAttrFinder;
	@Inject
	private TemporaryTimeOfDailyPerformanceFinder temporaryTimeFinder;
	@Inject
	private WorkInformationOfDailyFinder workInfoFinder;
	@Inject
	private TimeLeavingOfDailyPerformanceFinder timeLeavingFinder;
	@Inject
	private CalcAttrOfDailyPerformanceFinder calcAttrFinder;
	@Inject
	private ShortTimeOfDailyFinder shortWorkFinder;
	@Inject
	private EditStateOfDailyPerformanceFinder editStateFinder;
	@Inject
	private EmployeeDailyPerErrorFinder errorFinder;
	@Inject
	private AttendanceTimeByWorkOfDailyFinder attendanceTimeByWorkFinder;

	@SuppressWarnings("unchecked")
	@Override
	public DailyRecordDto find(String employeeId, GeneralDate baseDate) {
		DailyRecordDto result = new DailyRecordDto();
		result.setAffiliationInfo(affiliationInfoFinder.find(employeeId, baseDate));
		result.setAttendanceLeavingGate(Optional.of(attendanceLeavingGateFinder.find(employeeId, baseDate)));
		result.setAttendanceTime(Optional.of(attendanceItemFinder.find(employeeId, baseDate)));
		result.setBreakTime(breakItemFinder.finds(employeeId, baseDate));
		result.setOptionalItem(Optional.of(optionalItemFinder.find(employeeId, baseDate)));
		result.setOutingTime(Optional.of(outingTimeFinder.find(employeeId, baseDate)));
		result.setSpecificDateAttr(Optional.of(specificDateAttrFinder.find(employeeId, baseDate)));
		result.setTemporaryTime(Optional.of(temporaryTimeFinder.find(employeeId, baseDate)));
		result.setTimeLeaving(Optional.of(timeLeavingFinder.find(employeeId, baseDate)));
		result.setWorkInfo(workInfoFinder.find(employeeId, baseDate));
		result.setCalcAttr(calcAttrFinder.find(employeeId, baseDate));
		result.setShortWorkTime(Optional.of(shortWorkFinder.find(employeeId, baseDate)));
		result.setEditStates(editStateFinder.finds(employeeId, baseDate));
		result.setErrors(errorFinder.find(employeeId, baseDate));
		result.setAttendanceTimeByWork(Optional.of(attendanceTimeByWorkFinder.find(employeeId, baseDate)));
		return result;
	}

}
