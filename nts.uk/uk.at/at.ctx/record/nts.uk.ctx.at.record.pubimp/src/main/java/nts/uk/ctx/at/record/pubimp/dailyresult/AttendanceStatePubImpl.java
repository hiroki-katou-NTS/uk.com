package nts.uk.ctx.at.record.pubimp.dailyresult;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.dailyresult.adapter.DailyWorkScheduleAdapter;
import nts.uk.ctx.at.record.dom.dailyresult.service.AttendanceAccordActualData;
import nts.uk.ctx.at.record.dom.dailyresult.service.JudgingStatusDomainService;
import nts.uk.ctx.at.record.dom.dailyresult.service.JudgingStatusDomainServiceRequireImpl;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.pub.dailyresult.AttendanceStateExport;
import nts.uk.ctx.at.record.pub.dailyresult.AttendanceStatePub;
import nts.uk.ctx.at.record.pub.dailyresult.StatusClassfication;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class AttendanceStatePubImpl implements AttendanceStatePub {

	@Inject
	private WorkInformationRepository workInformationRepository;

	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;

	@Inject
	private DailyWorkScheduleAdapter dailyWorkScheduleAdapter;

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Override
	public AttendanceStateExport getAttendanceState(String sid) {
		
		JudgingStatusDomainServiceRequireImpl rq = 
				new JudgingStatusDomainServiceRequireImpl(
					workInformationRepository,
					timeLeavingOfDailyPerformanceRepository, 
					dailyWorkScheduleAdapter, 
					workTypeRepository
				);
		
		AttendanceAccordActualData data = JudgingStatusDomainService.JudgingStatus(rq, sid);
		
		return AttendanceStateExport.builder()
				.attendanceState(EnumAdaptor.valueOf(data.getAttendanceState().value,StatusClassfication.class))
				.workingNow(data.getWorkingNow())
				.build();
	}
}
