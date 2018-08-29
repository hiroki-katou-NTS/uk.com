package nts.uk.ctx.at.function.ac.widgetKtg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.AnnualLeaveGrantImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.AnnualLeaveManageInforImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.AnnualLeaveRemainingNumberImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.ApplicationTimeImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.AttendanceTimeImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.DailyExcessTotalTimeImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.DailyLateAndLeaveEarlyTimeImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.EmployeeErrorImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.KTGRsvLeaveInfoImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.NextAnnualLeaveGrantImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.NumAnnLeaReferenceDateImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.OptionalWidgetAdapter;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.OptionalWidgetImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.WidgetDisplayItemImport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyExcessTotalTimePub;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyExcessTotalTimePubImport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyLateAndLeaveEarlyTimePub;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyLateAndLeaveEarlyTimePubExport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyLateAndLeaveEarlyTimePubImport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.exportparam.DailyExcessTotalTimeExpParam;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnLeaveRemainNumberPub;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.export.AnnualLeaveGrantExport;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.export.AnnualLeaveManageInforExport;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.export.AnnualLeaveRemainingNumberExport;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.export.ReNumAnnLeaReferenceDateExport;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.GetRsvLeaNumCriteriaDate;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.RsvLeaNumByCriteriaDate;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.EmployeeDailyPerErrorPub;
import nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement.AnnualHolidayManagementPub;
import nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement.NextAnnualLeaveGrantExport;
import nts.uk.ctx.at.request.pub.application.lateorleaveearly.LateOrLeaveEarlyPub;
import nts.uk.ctx.at.request.pub.application.recognition.AppHdTimeNotReflectedPub;
import nts.uk.ctx.at.request.pub.application.recognition.AppNotReflectedPub;
import nts.uk.ctx.at.request.pub.application.recognition.ApplicationOvertimePub;
import nts.uk.ctx.at.request.pub.application.recognition.ApplicationTimeUnreflectedPub;
import nts.uk.ctx.at.request.pub.application.recognition.HolidayInstructPub;
import nts.uk.ctx.at.request.pub.application.recognition.OverTimeInstructPub;
import nts.uk.ctx.sys.portal.pub.toppagepart.optionalwidget.OptionalWidgetExport;
import nts.uk.ctx.sys.portal.pub.toppagepart.optionalwidget.OptionalWidgetPub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class OptionalWidgetImplementFinder implements OptionalWidgetAdapter {

	@Inject
	OverTimeInstructPub overTimeInstructPub;
	
	@Inject 
	HolidayInstructPub holidayInstructPub;
	
	@Inject
	private OptionalWidgetPub optionalWidgetPub;
	
	@Inject
	private EmployeeDailyPerErrorPub employeeDailyPerErrorPub;
	
	@Inject
	private ApplicationOvertimePub applicationOvertimePub;
	
	@Inject
	private DailyExcessTotalTimePub dailyExcessTotalTimePub;
	
	@Inject
	private ApplicationTimeUnreflectedPub applicationTimeUnreflectedPub;
	
	@Inject
	private AppHdTimeNotReflectedPub appHdTimeNotReflectedPub; 
	
	@Inject 
	private AppNotReflectedPub appNotReflectedPub;
	
	@Inject
	private AnnualHolidayManagementPub annualHolidayManagementPub;
	
	@Inject
	private AnnLeaveRemainNumberPub annLeaveRemainNumberPub;
	
	@Inject
	private LateOrLeaveEarlyPub lateOrLeaveEarlyPub;
	
	@Inject
	private DailyLateAndLeaveEarlyTimePub dailyLateAndLeaveEarlyTimePub;
	
	@Inject
	private GetRsvLeaNumCriteriaDate getRsvLeaNumCriteriaDate;
	
	@Override
	public int getNumberOT(String employeeId, GeneralDate startDate, GeneralDate endDate) {
		return overTimeInstructPub.acquireOverTimeWorkInstruction(employeeId, startDate, endDate).size();
	}

	@Override
	public int getNumberBreakIndication(String employeeId, GeneralDate startDate, GeneralDate endDate) {
		return holidayInstructPub.acquireBreakIndication(employeeId, startDate, endDate).size();
	}

	@Override
	public Optional<OptionalWidgetImport> getSelectedWidget(String companyId, String topPagePartCode) {
		Optional<OptionalWidgetExport> optionalWidgetExport = optionalWidgetPub.getSelectedWidget(companyId,
				topPagePartCode);
		if (!optionalWidgetExport.isPresent())
			return Optional.empty();

		List<WidgetDisplayItemImport> widgetDisplayItemImport = optionalWidgetExport.get().getWidgetDisplayItemExport()
				.stream().map(c -> new WidgetDisplayItemImport(c.getDisplayItemType(), c.getNotUseAtr()))
				.collect(Collectors.toList());

		Optional<OptionalWidgetImport> optionalWidgetImport = Optional.ofNullable(new OptionalWidgetImport(
				optionalWidgetExport.get().getTopPagePartID(), optionalWidgetExport.get().getTopPageCode(),
				optionalWidgetExport.get().getTopPageName(), optionalWidgetExport.get().getWidth(),
				optionalWidgetExport.get().getHeight(), widgetDisplayItemImport));
		return optionalWidgetImport;
	}

	@Override
	public List<EmployeeErrorImport> checkEmployeeErrorOnProcessingDate(String employeeId, DatePeriod datePeriod) {
		// TODO Auto-generated method stub
		return employeeDailyPerErrorPub.checkEmployeeErrorOnProcessingDate(employeeId, datePeriod).stream()
				.map(c -> new EmployeeErrorImport(c.getDate(), c.getHasError())).collect(Collectors.toList());
	}

	@Override
	public List<ApplicationTimeImport> acquireTotalApplicationOverTimeHours(String sId, GeneralDate startDate,
			GeneralDate endDate) {
		// TODO Auto-generated method stub
		return applicationOvertimePub.acquireTotalApplicationOverTimeHours(sId, startDate, endDate)
				.stream().map(c -> new ApplicationTimeImport(c.getDate(), c.getTotalOtHours()))
				.collect(Collectors.toList());
	}

	@Override
	public List<DailyExcessTotalTimeImport> getExcessTotalTime(String employeeId, DatePeriod datePeriod) {
		// TODO Auto-generated method stub
		Map<GeneralDate,DailyExcessTotalTimeExpParam> map =  dailyExcessTotalTimePub.getExcessTotalTime(new DailyExcessTotalTimePubImport(employeeId, datePeriod)).getMap();
		List<DailyExcessTotalTimeImport> result = new ArrayList<>();
		map.entrySet().forEach(c -> {
			result.add(new DailyExcessTotalTimeImport(c.getKey(), 
					new AttendanceTimeImport(c.getValue().getOverTime().hour(),c.getValue().getOverTime().minute()),
					new AttendanceTimeImport(c.getValue().getHolidayWorkTime().hour(),c.getValue().getHolidayWorkTime().minute()),
					new AttendanceTimeImport(c.getValue().getFlexOverTime().hour(),c.getValue().getFlexOverTime().minute()),
					new AttendanceTimeImport(c.getValue().getExcessMidNightTime().hour(),c.getValue().getExcessMidNightTime().minute())));
		});
		return result;
	}

	@Override
	public List<ApplicationTimeImport> acquireTotalApplicationTimeUnreflected(String sId, GeneralDate startDate,
			GeneralDate endDate) {
		// TODO Auto-generated method stub
		return applicationTimeUnreflectedPub.acquireTotalApplicationTimeUnreflected(sId, startDate, endDate)
				.stream().map(c -> new ApplicationTimeImport(c.getDate(), c.getTotalOtHours())).collect(Collectors.toList());
	}

	@Override
	public List<ApplicationTimeImport> acquireTotalAppHdTimeNotReflected(String sId, GeneralDate startDate,
			GeneralDate endDate) {
		// TODO Auto-generated method stub
		return appHdTimeNotReflectedPub.acquireTotalAppHdTimeNotReflected(sId, startDate, endDate)
				.stream().map(c -> new ApplicationTimeImport(c.getDate(), c.getBreakTime())).collect(Collectors.toList());
	}

	@Override
	public List<ApplicationTimeImport> acquireAppNotReflected(String sId, GeneralDate startDate, GeneralDate endDate) {
		// TODO Auto-generated method stub
		return appNotReflectedPub.acquireAppNotReflected(sId, startDate, endDate)
				.stream().map(c -> new ApplicationTimeImport(c.getDate(), c.getTotalOtHours())).collect(Collectors.toList());
	}

	@Override
	public List<NextAnnualLeaveGrantImport> acquireNextHolidayGrantDate(String cId, String employeeId, GeneralDate endDate) {
		// TODO Auto-generated method stub
		List<NextAnnualLeaveGrantExport> ListNext = annualHolidayManagementPub.acquireNextHolidayGrantDate(cId, employeeId, Optional.of(endDate));
		if(ListNext.isEmpty()) {
			return new ArrayList<>();
		}
		
		return ListNext.stream().map(c -> new NextAnnualLeaveGrantImport(c.getGrantDate(), 
																		c.getGrantDays().v(),
																		c.getTimes().v(), 
																		c.getTimeAnnualLeaveMaxDays().isPresent() ? c.getTimeAnnualLeaveMaxDays().get().v().intValue(): 0, 
																		c.getTimeAnnualLeaveMaxTime().isPresent()? c.getTimeAnnualLeaveMaxTime().get().v().intValue(): 0, 
																		c.getHalfDayAnnualLeaveMaxTimes().isPresent()?c.getHalfDayAnnualLeaveMaxTimes().get().v().intValue(): 0))
																		.collect(Collectors.toList());
		
		
		/*return new NextAnnualLeaveGrantImport(
				c.grantDate, 
				c.grantDays.v(), 
				c.times.v().intValue(), 
				c.timeAnnualLeaveMaxDays.isPresent() ? c.timeAnnualLeaveMaxDays.get().v().intValue(): 0, 
				c.timeAnnualLeaveMaxTime.isPresent()? c.timeAnnualLeaveMaxTime.get().v().intValue(): 0, 
				c.halfDayAnnualLeaveMaxTimes.isPresent()?c.halfDayAnnualLeaveMaxTimes.get().v().intValue(): 0);*/
	}

	@Override
	public NumAnnLeaReferenceDateImport getReferDateAnnualLeaveRemainNumber(String employeeID, GeneralDate date) {
		ReNumAnnLeaReferenceDateExport reNumAnnLeaReferenceDateExport = annLeaveRemainNumberPub.getReferDateAnnualLeaveRemainNumber(employeeID, date);
		AnnualLeaveRemainingNumberExport remainNumber = reNumAnnLeaReferenceDateExport.getAnnualLeaveRemainNumberExport();
		List<AnnualLeaveGrantExport> AnnualLeaveGrant = reNumAnnLeaReferenceDateExport.getAnnualLeaveGrantExports();
		List<AnnualLeaveManageInforExport> annualLeaveManageInforExports = reNumAnnLeaReferenceDateExport.getAnnualLeaveManageInforExports();
		AnnualLeaveRemainingNumberImport annualLeaveRemainNumberImport = null;
		if(remainNumber == null) {
			annualLeaveRemainNumberImport = new AnnualLeaveRemainingNumberImport(0.0, 0, 0, 0, 0.0, 0, 0, 0, 0.0,0.0);
		}else {
			annualLeaveRemainNumberImport = new AnnualLeaveRemainingNumberImport(
																				remainNumber.getAnnualLeaveGrantPreDay(),
																				remainNumber.getAnnualLeaveGrantPreTime(),
																				remainNumber.getNumberOfRemainGrantPre(),
																				remainNumber.getTimeAnnualLeaveWithMinusGrantPre(),
																				remainNumber.getAnnualLeaveGrantPostDay(),
																				remainNumber.getAnnualLeaveGrantPostTime(),
																				remainNumber.getNumberOfRemainGrantPost(),
																				remainNumber.getTimeAnnualLeaveWithMinusGrantPost(),
																				remainNumber.getAttendanceRate(),
																				remainNumber.getWorkingDays());
		}
		List<AnnualLeaveGrantImport> annualLeaveGrantImport = AnnualLeaveGrant.stream().map(c->new AnnualLeaveGrantImport(c.getGrantDate(), c.getGrantNumber(), c.getDaysUsedNo(), c.getUsedMinutes(), c.getRemainDays(), c.getRemainMinutes(), c.getDeadline())).collect(Collectors.toList());
		List<AnnualLeaveManageInforImport> annualLeaveManageInforImport = annualLeaveManageInforExports.stream().map(c->new AnnualLeaveManageInforImport(c.getYmd(), c.getDaysUsedNo(), c.getUsedMinutes(), c.getScheduleRecordAtr())).collect(Collectors.toList());
		
		return new NumAnnLeaReferenceDateImport(annualLeaveRemainNumberImport, annualLeaveGrantImport, annualLeaveManageInforImport);
	}

	@Override
	public List<DailyLateAndLeaveEarlyTimeImport> engravingCancelLateorLeaveearly(String employeeID, GeneralDate startDate,
			GeneralDate endDate) {
		
		return lateOrLeaveEarlyPub.engravingCancelLateorLeaveearly(employeeID, startDate, endDate).stream()
				.map(c -> new DailyLateAndLeaveEarlyTimeImport(c.getAppDate(), 
																c.getLate1()==1?true:false, 
																c.getEarly1()==1?true:false, 
																c.getLate2()==1?true:false, 
																c.getEarly2()==1?true:false)).collect(Collectors.toList());
	}

	@Override
	public List<DailyLateAndLeaveEarlyTimeImport> getLateLeaveEarly(String employeeId, DatePeriod datePeriod) {
		DailyLateAndLeaveEarlyTimePubExport map = dailyLateAndLeaveEarlyTimePub.getLateLeaveEarly(new DailyLateAndLeaveEarlyTimePubImport(employeeId, datePeriod));
		return map.getList().stream().map(c -> new DailyLateAndLeaveEarlyTimeImport(c.getDate(), c.isLate1(), c.isLeaveEarly1(), c.isLate2(), c.isLeaveEarly2())).collect(Collectors.toList());
	}

	@Override
	public KTGRsvLeaveInfoImport getNumberOfReservedYearsRemain(String employeeId, GeneralDate date) {
		Optional<RsvLeaNumByCriteriaDate> rsvLeaNumByCriteriaDate = getRsvLeaNumCriteriaDate.algorithm(employeeId, date);
		 
		if(rsvLeaNumByCriteriaDate.isPresent()) {
			RsvLeaNumByCriteriaDate rsvDate = rsvLeaNumByCriteriaDate.get();
			////付与日
			GeneralDate grantDay = GeneralDate.today();
			grantDay = rsvDate.getReserveLeaveInfo().getYmd();
			////付与前残数
			Double befRemainDay = rsvDate.getReserveLeaveInfo().getRemainingNumber().getReserveLeaveWithMinus()
									.getRemainingNumberBeforeGrant().getTotalRemainingDays().v();
			//付与後残数
			Double aftRemainDay = 0.0;
			if(rsvDate.getReserveLeaveInfo().getRemainingNumber().getReserveLeaveWithMinus().getRemainingNumberAfterGrant().isPresent()){
				aftRemainDay = rsvDate.getReserveLeaveInfo().getRemainingNumber().getReserveLeaveWithMinus().getRemainingNumberAfterGrant().get().getTotalRemainingDays().v();
			}
			return new KTGRsvLeaveInfoImport(befRemainDay, aftRemainDay, grantDay);
		}else {
			return new KTGRsvLeaveInfoImport(0.0, 0.0, GeneralDate.today());
		}
	}

	


}
