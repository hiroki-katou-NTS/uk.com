package nts.uk.ctx.at.record.app.find.dailyperform;

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
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.BreakTimeDailyFinder;
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
	private BreakTimeDailyFinder breakItemFinder;
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
		return DailyRecordDto.builder()
					.withWorkInfo(workInfoFinder.find(employeeId, baseDate))
						.withCalcAttr(calcAttrFinder.find(employeeId, baseDate))
							.withAffiliationInfo(affiliationInfoFinder.find(employeeId, baseDate))
								.withErrors(errorFinder.find(employeeId, baseDate))
							.outingTime(outingTimeFinder.find(employeeId, baseDate))
						.addBreakTime(breakItemFinder.finds(employeeId, baseDate))
					.attendanceTime(attendanceItemFinder.find(employeeId, baseDate))
						.attendanceTimeByWork(attendanceTimeByWorkFinder.find(employeeId, baseDate))
							.timeLeaving(timeLeavingFinder.find(employeeId, baseDate))
								.shortWorkTime(shortWorkFinder.find(employeeId, baseDate))
							.specificDateAttr(specificDateAttrFinder.find(employeeId, baseDate))
						.attendanceLeavingGate(attendanceLeavingGateFinder.find(employeeId, baseDate))
					.optionalItems(optionalItemFinder.find(employeeId, baseDate))
						.addEditStates(editStateFinder.finds(employeeId, baseDate))
							.temporaryTime(temporaryTimeFinder.find(employeeId, baseDate))
								.complete();
	}

}
