package nts.uk.ctx.at.record.dom.dailyresult.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyresult.adapter.DailyWorkScheduleAdapter;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@AllArgsConstructor
public class JudgingStatusDomainServiceRequireImpl implements JudgingStatusDomainService.Require {

	private WorkInformationRepository workInformationRepository;

	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;

	private DailyWorkScheduleAdapter dailyWorkScheduleAdapter;

	private WorkTypeRepository workTypeRepository;

	@Override
	public Optional<WorkInfoOfDailyPerformance> getDailyActualWorkInfo(String sid, GeneralDate baseDate) {
		return workInformationRepository.find(sid, baseDate);
	}

	@Override
	public Optional<TimeLeavingOfDailyPerformance> getDailyAttendanceAndDeparture(String sid, GeneralDate baseDate) {
		return timeLeavingOfDailyPerformanceRepository.findByKey(sid, baseDate);
	}

	@Override
	public Optional<String> getDailyWorkScheduleWorkTypeCode(String sid, GeneralDate baseDate) {
		return dailyWorkScheduleAdapter.getWorkTypeCode(sid, baseDate);
	}

	@Override
	public Optional<WorkType> getWorkType(String loginCid, String code) {
		return workTypeRepository.findByPK(loginCid, code);
	}
}
