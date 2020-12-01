package nts.uk.ctx.at.record.ac.workschedule;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workschedule.BreakTimeOfDailyAttdImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.BreakTimeSheetImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.ReasonTimeChangeImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.TimeActualStampImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.TimeLeavingOfDailyAttdImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.TimeLeavingWorkImport;
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
		return data.map(this::convert);
	}

	@Override
	public List<WorkScheduleWorkInforImport> getList(List<String> sids, DatePeriod period) {
		return workSchedulePub.getList(sids, period).stream().map(this::convert).collect(Collectors.toList());
	}

	private WorkScheduleWorkInforImport convert(WorkScheduleExport data){
		List<BreakTimeOfDailyAttdImport> listBreakTimeOfDailyAttdImport = data.getListBreakTimeOfDaily()
				.stream()
				.map(c -> new BreakTimeOfDailyAttdImport(c.getBreakType(),
						c.getBreakTimeSheets().stream()
								.map(x -> new BreakTimeSheetImport(x.getBreakFrameNo(), x.getStartTime(),
										x.getEndTime(), x.getBreakTime()))
								.collect(Collectors.toList())))
				.collect(Collectors.toList());
		return new WorkScheduleWorkInforImport(data.getWorkTyle(), data.getWorkTime(),
				data.getGoStraightAtr(), data.getBackStraightAtr(),
				!data.getTimeLeavingOfDailyAttd().isPresent() ? null
						: new TimeLeavingOfDailyAttdImport(
						data.getTimeLeavingOfDailyAttd().get().getTimeLeavingWorks().stream()
								.map(this::convertToTimeLeavingWork).collect(Collectors.toList()),
						data.getTimeLeavingOfDailyAttd().get().getWorkTimes()),
				listBreakTimeOfDailyAttdImport);
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

}
