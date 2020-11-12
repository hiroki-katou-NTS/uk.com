package nts.uk.ctx.at.record.dom.adapter.workschedule.snapshot;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface DailySnapshotWorkAdapter {

	Optional<DailySnapshotWorkImport> find(String sid, GeneralDate ymd);
	
	List<DailySnapshotWorkImport> find(String sid, DatePeriod ymd);
	
	Optional<DailySnapshotWorkImport> createFromSchedule(String sid, GeneralDate ymd);
	
	void save(DailySnapshotWorkImport snapshot);
	
	void update(DailySnapshotWorkImport snapshot);
}
