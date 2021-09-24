package nts.uk.ctx.at.record.infra.repository.daily.timegroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.daily.timegroup.TaskTimeGroup;
import nts.uk.ctx.at.record.dom.daily.timegroup.TaskTimeGroupRepository;
import nts.uk.ctx.at.record.dom.daily.timegroup.TaskTimeZone;
import nts.uk.ctx.at.record.infra.entity.daily.timegroup.KsrdtTaskTsGroup;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.favoritetaskitem.KrcdtTaskFavFrameSet;

/**
 * 
 * @author tutt
 *
 */
public class JpaTaskTimeGroupRepository extends JpaRepository implements TaskTimeGroupRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT g FROM KsrdtTaskTsGroup g";
	private static final String SELECT_BY_SID = SELECT_ALL_QUERY_STRING + " WHERE g.sId = :sId";
	private static final String SELECT_BY_SID_AND_DATE = SELECT_BY_SID + " AND g.pk.date = :date";
	private static final String SELECT_BY_SID_AND_PERIOD = SELECT_BY_SID + " AND g.pk.date >= :start AND g.pk.date <= :end";

	@Override
	public void insert(TaskTimeGroup tg) {

		for (TaskTimeZone t : tg.getTimezones()) {
			this.commandProxy().insert(new KsrdtTaskTsGroup(t, tg.getSId(), tg.getDate()));
		}
	}

	@Override
	public void delete(String sId, GeneralDate date) {

		Optional<KsrdtTaskTsGroup> entity = this.queryProxy().query(SELECT_BY_SID_AND_DATE, KsrdtTaskTsGroup.class)
				.setParameter("date", date).setParameter("sId", sId).getSingle();

		if (entity.isPresent()) {
			this.commandProxy().remove(entity.get());
		}
	}

	@Override
	public Optional<TaskTimeGroup> get(String sId, GeneralDate date) {
		List<KsrdtTaskTsGroup> entities = this.queryProxy().query(SELECT_BY_SID_AND_DATE, KsrdtTaskTsGroup.class)
				.setParameter("date", date)
				.setParameter("sId", sId)
				.getList();
		
		List<TaskTimeZone> timezones = new ArrayList<>();
		
		if (entities.isEmpty()) {
			return Optional.empty();
		}
		
		for (KsrdtTaskTsGroup group : entities) {
			timezones.add(group.toDomain());
		}
		
		return Optional.of(new TaskTimeGroup(sId, date, timezones));
	}

	@Override
	public List<TaskTimeGroup> get(String sId, DatePeriod period) {
		GeneralDate start = period.start();
		GeneralDate end = period.end();
		
		List<KsrdtTaskTsGroup> entities = this.queryProxy().query(SELECT_BY_SID_AND_PERIOD, KsrdtTaskTsGroup.class)
				.setParameter("start", start)
				.setParameter("end", end)
				.setParameter("sId", sId)
				.getList();
		
		List<TaskTimeGroup> timeGroups = new ArrayList<>();
		
		if (entities.isEmpty()) {
			return timeGroups;
		}
		
		List<TaskTimeZone> timezones = new ArrayList<>();
		
		for (KsrdtTaskTsGroup group : entities) {
			timezones.add(group.toDomain());
		}
		
		List<GeneralDate> dates = period.datesBetween();
		
		for (GeneralDate date : dates) {
			timeGroups.add(new TaskTimeGroup(sId, date, timezones));
		}
		
		return timeGroups;
	}

}
