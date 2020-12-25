package nts.uk.ctx.at.schedule.pub.schedule.workschedule.snapshot;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface DailySnapshotWorkPub {
	
	Optional<DailySnapshotWorkExport> find(String sid, GeneralDate ymd);
	
	List<DailySnapshotWorkExport> find(String sid, DatePeriod ymd);
	
	Optional<DailySnapshotWorkExport> createFromSchedule(String sid, GeneralDate ymd);
	
	void save(DailySnapshotWorkExport snapshot);
	
	void update(DailySnapshotWorkExport snapshot);
}
