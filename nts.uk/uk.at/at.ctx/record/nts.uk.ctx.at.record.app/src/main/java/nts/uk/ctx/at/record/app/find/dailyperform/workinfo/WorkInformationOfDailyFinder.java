package nts.uk.ctx.at.record.app.find.dailyperform.workinfo;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.ScheduleTimeZoneDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInfoDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class WorkInformationOfDailyFinder extends FinderFacade {

	@Inject
	private WorkInformationRepository workInfoRepo;

	@Override
	@SuppressWarnings("unchecked")
	public WorkInformationOfDailyDto find(String employeeId, GeneralDate baseDate) {
		WorkInfoOfDailyPerformance workInfoOpt = this.workInfoRepo.find(employeeId, baseDate).orElse(null);
		WorkInformationOfDailyDto result = new WorkInformationOfDailyDto();
		if (workInfoOpt != null) {
			result.setActualWorkInfo(createWorkInfo(workInfoOpt.getRecordWorkInformation()));
			result.setBackStraightAtr(workInfoOpt.getBackStraightAtr().value);
			result.setCalculationState(workInfoOpt.getCalculationState().value);
			result.setGoStraightAtr(workInfoOpt.getGoStraightAtr().value);
			result.setPlanWorkInfo(createWorkInfo(workInfoOpt.getScheduleWorkInformation()));
			result.setScheduleTimeZone(workInfoOpt.getScheduleTimeSheets().stream().map(sts -> {
				return new ScheduleTimeZoneDto(sts.getWorkNo().v().intValue(), sts.getAttendance().v(),
						sts.getLeaveWork().v());
			}).sorted((s1, s2) -> s1.getWorkNo().compareTo(s2.getWorkNo())).collect(Collectors.toList()));
		}
		return result;
	}

	private WorkInfoDto createWorkInfo(WorkInformation workInfo) {
		return workInfo == null ? null : new WorkInfoDto(workInfo.getWorkTypeCode().v(),
				workInfo.getWorkTimeCode().v());
	}

}
