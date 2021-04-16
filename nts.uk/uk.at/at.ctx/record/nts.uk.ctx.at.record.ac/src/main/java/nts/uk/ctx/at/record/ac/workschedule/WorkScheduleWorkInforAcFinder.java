package nts.uk.ctx.at.record.ac.workschedule;

import java.util.*;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workschedule.ActualWorkingTimeOfDailyImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.AttendanceTimeOfDailyAttendanceImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.BreakTimeOfDailyAttdImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.BreakTimeSheetImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.ReasonTimeChangeImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.TimeActualStampImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.TimeLeavingOfDailyAttdImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.TimeLeavingWorkImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.TotalWorkingTimeImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleBasicInforRecordImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkStampImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkTimeInformationImport;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.TimeActualStampExport;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.TimeLeavingWorkExport;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.WorkScheduleExport;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.WorkSchedulePub;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.WorkStampExport;

/**
 * 
 * @author tutk
 *
 */
@Stateless
public class WorkScheduleWorkInforAcFinder implements WorkScheduleWorkInforAdapter {
	
	@Inject
	private WorkSchedulePub workSchedulePub;

	@Override
	public Optional<WorkScheduleWorkInforImport> get(String employeeID, GeneralDate ymd) {
		Optional<WorkScheduleExport> data = workSchedulePub.get(employeeID, ymd);
		if (data.isPresent()) {
			BreakTimeOfDailyAttdImport listBreakTimeOfDailyAttdImport = new BreakTimeOfDailyAttdImport(
					data.get().getListBreakTimeOfDaily().get().getBreakTimeSheets().stream()
							.map(x -> new BreakTimeSheetImport(x.getBreakFrameNo(), x.getStartTime(),
									x.getEndTime(), x.getBreakTime()))
							.collect(Collectors.toList()));
			return Optional.of(new WorkScheduleWorkInforImport(data.get().getEmployeeId(),
					data.get().getConfirmedATR(), data.get().getWorkTyle(), data.get().getWorkTime(),
					data.get().getGoStraightAtr(), data.get().getBackStraightAtr(),
					!data.get().getTimeLeavingOfDailyAttd().isPresent() ? null
							: new TimeLeavingOfDailyAttdImport(
							data.get().getTimeLeavingOfDailyAttd().get().getTimeLeavingWorks().stream()
									.map(c -> convertToTimeLeavingWork(c)).collect(Collectors.toList()),
							data.get().getTimeLeavingOfDailyAttd().get().getWorkTimes()),
					listBreakTimeOfDailyAttdImport, ymd, null));
		}
		return Optional.empty();
	}

	@Override
	public List<WorkScheduleWorkInforImport> getBy(List<String> sids, DatePeriod period) {
		return workSchedulePub.getList(sids, period).stream().map(this::convert).collect(Collectors.toList());
	}

	private WorkScheduleWorkInforImport convert(WorkScheduleExport data) {
		Optional<BreakTimeOfDailyAttdImport> listBreakTimeOfDailyAttdImport = data.getListBreakTimeOfDaily()
				.map(c -> new BreakTimeOfDailyAttdImport(
						c.getBreakTimeSheets().stream()
								.map(x -> new BreakTimeSheetImport(x.getBreakFrameNo(), x.getStartTime(),
										x.getEndTime(), x.getBreakTime()))
								.collect(Collectors.toList())));
		
		AttendanceTimeOfDailyAttendanceImport attendanceImport = null;
		if (data.getOptAttendanceTime().isPresent()) {
			ActualWorkingTimeOfDailyImport actualWorkingTimeOfDaily = ActualWorkingTimeOfDailyImport.builder()
					.totalWorkingTime(TotalWorkingTimeImport.builder()
							.actualTime(data.getOptAttendanceTime().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getActualTime()).build())
					.build();
			attendanceImport = AttendanceTimeOfDailyAttendanceImport.builder()
					.actualWorkingTimeOfDaily(actualWorkingTimeOfDaily)
					.build();
		}
		
		return new WorkScheduleWorkInforImport(data.getEmployeeId(), data.getConfirmedATR(), data.getWorkTyle(),
				data.getWorkTime(), data.getGoStraightAtr(), data.getBackStraightAtr(),
				!data.getTimeLeavingOfDailyAttd().isPresent() ? null
						: new TimeLeavingOfDailyAttdImport(
						data.getTimeLeavingOfDailyAttd().get().getTimeLeavingWorks().stream()
								.map(this::convertToTimeLeavingWork).collect(Collectors.toList()),
						data.getTimeLeavingOfDailyAttd().get().getWorkTimes()),
				listBreakTimeOfDailyAttdImport.orElse(null),
				data.getYmd(),
				attendanceImport);
	}

	private TimeLeavingWorkImport convertToTimeLeavingWork(TimeLeavingWorkExport domain) {
		return new TimeLeavingWorkImport(domain.getWorkNo(),
				!domain.getAttendanceStamp().isPresent() ? null
						: convertToTimeActualStamp(domain.getAttendanceStamp().get()),
				!domain.getLeaveStamp().isPresent() ? null : convertToTimeActualStamp(domain.getLeaveStamp().get()));
	}

	private TimeActualStampImport convertToTimeActualStamp(TimeActualStampExport domain) {
		return new TimeActualStampImport(
				domain.getActualStamp().isPresent() ? convertToWorkStamp(domain.getActualStamp().get()) : null,
				domain.getStamp().isPresent() ? convertToWorkStamp(domain.getStamp().get()) : null,
				domain.getNumberOfReflectionStamp());
	}

	private WorkStampImport convertToWorkStamp(WorkStampExport domain) {
		return new WorkStampImport(domain.getTimeDay().getTimeWithDay(), new WorkTimeInformationImport(
				new ReasonTimeChangeImport(domain.getTimeDay().getReasonTimeChange().getTimeChangeMeans(),
						domain.getTimeDay().getReasonTimeChange().getEngravingMethod()),
				domain.getTimeDay().getTimeWithDay()),
				domain.getLocationCode());
	}

	@Override
	public List<WorkScheduleBasicInforRecordImport> getList(List<String> sid, DatePeriod dPeriod) {
		return workSchedulePub.get(sid, dPeriod).stream()
				.map(x -> new WorkScheduleBasicInforRecordImport(x.getEmployeeID(),
						x.getYmd(), x.getWorkTypeCd(), x.getWorkTimeCd()))
				.collect(Collectors.toList());
	}

	
}
