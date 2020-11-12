package nts.uk.ctx.at.schedule.infra.repository.schedule.workschedules.snapshot;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.snapshot.DailySnapshotWork;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.snapshot.DailySnapshotWorkRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.snapshot.KscdtSnapshot;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.snapshot.KscdtSnapshotPK;

@Stateless
public class JpaDailySnapshotWorkRepoImpl extends JpaRepository implements DailySnapshotWorkRepository {

	@Override
	public Optional<DailySnapshotWork> find(String sid, GeneralDate ymd) {
		
		return this.queryProxy().find(new KscdtSnapshotPK(sid, ymd), KscdtSnapshot.class)
				.map(c -> c.domain());
	}

	@Override
	public List<DailySnapshotWork> find(String sid, DatePeriod ymd) {
		
		return this.queryProxy().query("SELECT s FROM KscdtSnapshot s WHERE s.pk.sid = :sid"
				+ " AND s.pk.ymd >= :start AND s.pk.ymd <= :end", KscdtSnapshot.class)
				.setParameter("sid", sid)
				.setParameter("start", ymd.start())
				.setParameter("end", ymd.end())
				.getList(c -> c.domain());
	}

	@Override
	public void save(DailySnapshotWork snapshot) {

		this.commandProxy().insert(KscdtSnapshot.create(snapshot));
	}

	@Override
	public void update(DailySnapshotWork snapshot) {
		val entity = this.queryProxy().find(new KscdtSnapshotPK(snapshot.getSid(), snapshot.getYmd()), KscdtSnapshot.class);
		
		if (entity.isPresent()) {
			entity.ifPresent(e -> {
				e.predeterminedTime = snapshot.getSnapshot().getPredetermineTime().valueAsMinutes();
				e.workTypeCd = snapshot.getSnapshot().getWorkInfo().getWorkTypeCode().v();
				e.workTimeCd = snapshot.getSnapshot().getWorkInfo().getWorkTimeCodeNotNull().map(c -> c.v()).orElse(null);
				
				this.commandProxy().update(e);
			});
		} else {
			
			save(snapshot);
		}
	}

	@Override
	public void delete(String sid, GeneralDate ymd) {

		this.queryProxy().find(new KscdtSnapshotPK(sid, ymd), KscdtSnapshot.class)
							.ifPresent(e -> this.commandProxy().remove(e));
	}

}
