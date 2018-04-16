package nts.uk.ctx.at.record.infra.repository.daily.attendanceleavinggate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.AttendanceLeavingGateOfDailyRepo;
import nts.uk.ctx.at.record.infra.entity.daily.attendanceleavinggate.KrcdtDayLeaveGate;
import nts.uk.ctx.at.record.infra.entity.daily.attendanceleavinggate.KrcdtDayLeaveGatePK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AttendanceLeavingGateOfDailyRepoImpl extends JpaRepository implements AttendanceLeavingGateOfDailyRepo {

	private static final String REMOVE_BY_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDayLeaveGate a ");
		builderString.append("WHERE a.id.sid = :employeeId ");
		builderString.append("AND a.id.ymd = :ymd ");
		REMOVE_BY_KEY = builderString.toString();
	}

	@Override
	public Optional<AttendanceLeavingGateOfDaily> find(String employeeId, GeneralDate baseDate) {
		return this.queryProxy().find(new KrcdtDayLeaveGatePK(employeeId, baseDate), KrcdtDayLeaveGate.class)
				.map(c -> c.toDomain());
	}

	@Override
	public List<AttendanceLeavingGateOfDaily> find(String employeeId, List<GeneralDate> baseDate) {
		if (baseDate.isEmpty()) {
			return Collections.emptyList();
		}
		return this.queryProxy()
				.query("SELECT al FROM KrcdtDayLeaveGate al WHERE al.id.sid = :sid AND al.id.ymd IN :ymd",
						KrcdtDayLeaveGate.class)
				.setParameter("ymd", baseDate).setParameter("sid", employeeId).getList(c -> c.toDomain());
	}

	@Override
	public List<AttendanceLeavingGateOfDaily> find(String employeeId) {
		return this.queryProxy()
				.query("SELECT al FROM KrcdtDayLeaveGate al WHERE al.id.sid = :sid", KrcdtDayLeaveGate.class)
				.setParameter("sid", employeeId).getList(c -> c.toDomain());
	}

	@Override
	public List<AttendanceLeavingGateOfDaily> finds(List<String> employeeId, DatePeriod baseDate) {
		if (employeeId.isEmpty()) {
			return Collections.emptyList();
		}
		return this.queryProxy()
				.query("SELECT al FROM KrcdtDayLeaveGate al WHERE al.id.sid IN :sid AND al.id.ymd <= :end AND al.id.ymd >= :start",
						KrcdtDayLeaveGate.class)
				.setParameter("end", baseDate.end()).setParameter("start", baseDate.start())
				.setParameter("sid", employeeId).getList(c -> c.toDomain());
	}

	@Override
	public void update(AttendanceLeavingGateOfDaily domain) {
		this.queryProxy()
				.find(new KrcdtDayLeaveGatePK(domain.getEmployeeId(), domain.getYmd()), KrcdtDayLeaveGate.class)
				.ifPresent(entity -> {
					entity.mergeData(domain);
					this.commandProxy().update(entity);
				});
	}

	@Override
	public void add(AttendanceLeavingGateOfDaily domain) {
		this.commandProxy().insert(KrcdtDayLeaveGate.from(domain));
	}

	@Override
	public void remove(AttendanceLeavingGateOfDaily domain) {
		this.queryProxy()
				.find(new KrcdtDayLeaveGatePK(domain.getEmployeeId(), domain.getYmd()), KrcdtDayLeaveGate.class)
				.ifPresent(entity -> {
					this.commandProxy().remove(entity);
				});
	}

	@Override
	public void removeByKey(String employeeId, GeneralDate baseDate) {
		this.getEntityManager().createQuery(REMOVE_BY_KEY).setParameter("employeeId", employeeId)
				.setParameter("ymd", baseDate).executeUpdate();
		this.getEntityManager().flush();
	}

}
