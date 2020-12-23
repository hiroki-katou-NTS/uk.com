package nts.uk.ctx.at.schedule.dom.schedule.workschedule.snapshot;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface DailySnapshotWorkRepository {

	Optional<DailySnapshotWork> find(String sid, GeneralDate ymd);

	List<DailySnapshotWork> find(String sid, DatePeriod ymd);
	
	void save(DailySnapshotWork snapshot);
	
	void update(DailySnapshotWork snapshot);
	
	void delete(String sid, GeneralDate ymd);
}
