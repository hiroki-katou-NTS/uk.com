package nts.uk.ctx.at.record.ac.schedule.snapshot;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.schedule.snapshot.DailySnapshotWorkAdapter;
import nts.uk.ctx.at.record.dom.adapter.schedule.snapshot.DailySnapshotWorkImport;
import nts.uk.ctx.at.record.dom.adapter.schedule.snapshot.SnapshotImport;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.snapshot.DailySnapshotWorkExport;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.snapshot.DailySnapshotWorkPub;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.snapshot.SnapShotExport;

@Stateless
public class DailySnapshotWorkAdapterImpl implements DailySnapshotWorkAdapter {

	@Inject
	private DailySnapshotWorkPub snapshotPub;
	
	@Override
	public Optional<DailySnapshotWorkImport> find(String sid, GeneralDate ymd) {
		
		return snapshotPub.find(sid, ymd).map(c -> convert(c));
	}

	@Override
	public void save(DailySnapshotWorkImport snapshot) {
		
		DailySnapshotWorkExport domain = DailySnapshotWorkExport.builder()
				.sid(snapshot.getSid())
				.ymd(snapshot.getYmd())
				.snapshot(SnapShotExport.builder()
						.workTime(snapshot.getSnapshot().getWorkTime())
						.workType(snapshot.getSnapshot().getWorkType())
						.predetermineTime(snapshot.getSnapshot().getPredetermineTime())
						.build())
				.build();
		snapshotPub.save(domain);
	}

	@Override
	public void update(DailySnapshotWorkImport snapshot) {
		
		DailySnapshotWorkExport domain = DailySnapshotWorkExport.builder()
				.sid(snapshot.getSid())
				.ymd(snapshot.getYmd())
				.snapshot(SnapShotExport.builder()
						.workTime(snapshot.getSnapshot().getWorkTime())
						.workType(snapshot.getSnapshot().getWorkType())
						.predetermineTime(snapshot.getSnapshot().getPredetermineTime())
						.build())
				.build();
		snapshotPub.save(domain);
	}

	@Override
	public Optional<DailySnapshotWorkImport> createFromSchedule(String sid, GeneralDate ymd) {
		
		return snapshotPub.createFromSchedule(sid, ymd).map(c -> convert(c));
	}

	private DailySnapshotWorkImport convert(DailySnapshotWorkExport c) {
		return DailySnapshotWorkImport.builder()
				.sid(c.getSid())
				.ymd(c.getYmd())
				.snapshot(SnapshotImport.builder()
						.workTime(c.getSnapshot().getWorkTime())
						.workType(c.getSnapshot().getWorkType())
						.predetermineTime(c.getSnapshot().getPredetermineTime())
						.build())
				.build();
	}

	@Override
	public List<DailySnapshotWorkImport> find(String sid, DatePeriod ymd) {
		
		return snapshotPub.find(sid, ymd).stream().map(c -> convert(c)).collect(Collectors.toList());
	}
}
