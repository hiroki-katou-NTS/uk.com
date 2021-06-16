package nts.uk.ctx.at.record.dom.adapter.workschedule;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.adapter.workschedule.BreakTimeOfDailyAttdSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.workschedule.BreakTimeSheetSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.workschedule.ReasonTimeChangeSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.workschedule.TimeActualStampSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.workschedule.TimeLeavingOfDailyAttdSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.workschedule.TimeLeavingWorkSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.workschedule.WorkScheWorkInforSharedAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workschedule.WorkScheduleWorkSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.workschedule.WorkStampSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.workschedule.WorkTimeInforSharedImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

@Stateless
public class WorkScheWorkInforSharedImpl implements WorkScheWorkInforSharedAdapter {

	@Inject
	private WorkScheduleWorkInforAdapter workScheduleWorkInforAdapter;
	
	@Inject
	private WorkInformationRepository workInformationRepository;
	
	
	@Override
	public Optional<WorkScheduleWorkSharedImport> get(String employeeID, GeneralDate ymd) {
		Optional<WorkScheduleWorkInforImport> data = workScheduleWorkInforAdapter.get(employeeID, ymd);
		if (data.isPresent()) {
			BreakTimeOfDailyAttdSharedImport listBreakTimeOfDailyAttdImport = new BreakTimeOfDailyAttdSharedImport(
					data.get().getBreakTime().getBreakTimeSheets().stream()
									.map(x -> new BreakTimeSheetSharedImport(x.getBreakFrameNo(), x.getStartTime(),
											x.getEndTime(), x.getBreakTime()))
									.collect(Collectors.toList()));
			return Optional.of(new WorkScheduleWorkSharedImport(
					data.get().getWorkType(), 
					data.get().getWorkTime(),
					data.get().getGoStraightAtr(), 
					data.get().getBackStraightAtr(),
					!data.get().getTimeLeaving().isPresent() ? null
							: new TimeLeavingOfDailyAttdSharedImport(
									data.get().getTimeLeaving().get().getTimeLeavingWorks().stream()
											.map(c -> convertToTimeLeavingWork(c)).collect(Collectors.toList()),
									data.get().getTimeLeaving().get().getWorkTimes()),
					listBreakTimeOfDailyAttdImport));
		}
		return Optional.empty();
	}
	
	private TimeLeavingWorkSharedImport convertToTimeLeavingWork(TimeLeavingWorkImport domain) {
		return new TimeLeavingWorkSharedImport(domain.getWorkNo(),
				!domain.getAttendanceStamp().isPresent() ? null
						: convertToTimeActualStamp(domain.getAttendanceStamp().get()),
				!domain.getLeaveStamp().isPresent() ? null : convertToTimeActualStamp(domain.getLeaveStamp().get()));
	}

	private TimeActualStampSharedImport convertToTimeActualStamp(TimeActualStampImport domain) {
		return new TimeActualStampSharedImport(
				domain.getActualStamp().isPresent() ? convertToWorkStamp(domain.getActualStamp().get()) : null,
				domain.getStamp().isPresent() ? convertToWorkStamp(domain.getStamp().get()) : null,
				domain.getNumberOfReflectionStamp());
	}

	private WorkStampSharedImport convertToWorkStamp(WorkStampImport domain) {
		return new WorkStampSharedImport(domain.getTimeDay().getTimeWithDay(),
				new WorkTimeInforSharedImport(
				new ReasonTimeChangeSharedImport(domain.getTimeDay().getReasonTimeChange().getTimeChangeMeans(),
						domain.getTimeDay().getReasonTimeChange().getEngravingMethod()),
				domain.getTimeDay().getTimeWithDay()),
				domain.getLocationCode());
	}

	@Override
	public Optional<WorkInfoOfDailyAttendance> getWorkInfoOfDailyAttendance(String employeeID, GeneralDate ymd) {
		Optional<WorkInfoOfDailyPerformance> data = workInformationRepository.find(employeeID, ymd);
		if(data.isPresent()) {
			return Optional.of(data.get().getWorkInformation());
		}
		return Optional.empty();
	}

}
