package nts.uk.ctx.at.schedule.pubimp.schedule.workschedule.snapshot;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.snapshot.CreateScheduledSnapshotService;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.snapshot.DailySnapshotWork;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.snapshot.DailySnapshotWorkRepository;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.snapshot.DailySnapshotWorkExport;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.snapshot.DailySnapshotWorkPub;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.snapshot.SnapShotExport;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;

@Stateless
public class DailySnapshotWorkPubImpl implements DailySnapshotWorkPub {

	@Inject
	private DailySnapshotWorkRepository repo;
	
	@Inject
	private WorkScheduleRepository workScheduleRepo;
	
	@Override
	public Optional<DailySnapshotWorkExport> find(String sid, GeneralDate ymd) {
		
		return repo.find(sid, ymd).map(c -> convert(sid, ymd, c.getSnapshot()));
	}

	@Override
	public void save(DailySnapshotWorkExport snapshot) {
		
		DailySnapshotWork domain = DailySnapshotWork.of(snapshot.getSid(), 
														snapshot.getYmd(), 
														SnapShot.of(new WorkInformation(snapshot.getSnapshot().getWorkType(), 
																						snapshot.getSnapshot().getWorkTime().orElse(null)), 
																	new AttendanceTime(snapshot.getSnapshot().getPredetermineTime())));
		repo.save(domain);
	}

	@Override
	public void update(DailySnapshotWorkExport snapshot) {
		
		DailySnapshotWork domain = DailySnapshotWork.of(snapshot.getSid(), 
														snapshot.getYmd(), 
														SnapShot.of(new WorkInformation(snapshot.getSnapshot().getWorkType(), 
																						snapshot.getSnapshot().getWorkTime().orElse(null)), 
																	new AttendanceTime(snapshot.getSnapshot().getPredetermineTime())));
		repo.update(domain);
	}

	@Override
	public Optional<DailySnapshotWorkExport> createFromSchedule(String sid, GeneralDate ymd) {
		
		val snapshot = CreateScheduledSnapshotService.createForScheduleManaged(
				new CreateScheduledSnapshotService.RequireM1() {
					
			@Override
			public Optional<WorkSchedule> workSchedule(String sid, GeneralDate ymd) {
				
				return workScheduleRepo.get(sid, ymd);
			}
		}, sid, ymd);
		
		return snapshot.map(ss -> convert(sid, ymd, ss));
	}

	private DailySnapshotWorkExport convert(String sid, GeneralDate ymd, SnapShot ss) {
		return DailySnapshotWorkExport.builder()
				.sid(sid)
				.ymd(ymd)
				.snapshot(SnapShotExport.builder()
						.workTime(ss.getWorkInfo().getWorkTimeCodeNotNull().map(v -> v.v()))
						.workType(ss.getWorkInfo().getWorkTypeCode().v())
						.predetermineTime(ss.getPredetermineTime().v())
						.build())
				.build();
	}

	@Override
	public List<DailySnapshotWorkExport> find(String sid, DatePeriod ymd) {
		
		return repo.find(sid, ymd).stream()
					.map(c -> convert(c.getSid(), c.getYmd(), c.getSnapshot()))
					.collect(Collectors.toList());
	}

	
}
