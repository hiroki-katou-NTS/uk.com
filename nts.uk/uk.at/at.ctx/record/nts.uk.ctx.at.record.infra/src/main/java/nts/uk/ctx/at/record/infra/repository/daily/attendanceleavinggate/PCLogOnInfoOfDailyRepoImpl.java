package nts.uk.ctx.at.record.infra.repository.daily.attendanceleavinggate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.PCLogOnInfoOfDailyRepo;
import nts.uk.ctx.at.record.infra.entity.daily.attendanceleavinggate.KrcdtDayPcLogonInfo;
import nts.uk.ctx.at.record.infra.entity.daily.attendanceleavinggate.KrcdtDayPcLogonInfoPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class PCLogOnInfoOfDailyRepoImpl extends JpaRepository implements PCLogOnInfoOfDailyRepo {

	@Override
	public Optional<PCLogOnInfoOfDaily> find(String employeeId, GeneralDate baseDate) {
		return this.queryProxy().find(new KrcdtDayPcLogonInfoPK(employeeId, baseDate), KrcdtDayPcLogonInfo.class)
				.map(c -> c.toDomain());
	}

	@Override
	public List<PCLogOnInfoOfDaily> find(String employeeId, List<GeneralDate> baseDate) {
		if (baseDate.isEmpty()) {
			return Collections.emptyList();
		}
		return this.queryProxy()
				.query("SELECT al FROM KrcdtDayPcLogonInfo al WHERE al.id.sid = :sid AND al.id.ymd IN :ymd",
						KrcdtDayPcLogonInfo.class)
				.setParameter("ymd", baseDate).setParameter("sid", employeeId).getList(c -> c.toDomain());
	}

	@Override
	public List<PCLogOnInfoOfDaily> find(String employeeId) {
		return this.queryProxy()
				.query("SELECT al FROM KrcdtDayPcLogonInfo al WHERE al.id.sid = :sid", KrcdtDayPcLogonInfo.class)
				.setParameter("sid", employeeId).getList(c -> c.toDomain());
	}

	@Override
	public List<PCLogOnInfoOfDaily> finds(List<String> employeeId, DatePeriod baseDate) {
		if (employeeId.isEmpty()) {
			return Collections.emptyList();
		}
		return this.queryProxy()
				.query("SELECT al FROM KrcdtDayPcLogonInfo al WHERE al.id.sid = :sid AND al.id.ymd <= :end AND al.id.ymd >= :start",
						KrcdtDayPcLogonInfo.class)
				.setParameter("end", baseDate.end()).setParameter("start", baseDate.start())
				.setParameter("sid", employeeId).getList(c -> c.toDomain());
	}

	@Override
	public void update(PCLogOnInfoOfDaily domain) {
		this.queryProxy()
				.find(new KrcdtDayPcLogonInfoPK(domain.getEmployeeId(), domain.getYmd()), KrcdtDayPcLogonInfo.class)
				.ifPresent(entity -> {
					entity.mergeData(domain);
					this.commandProxy().update(entity);
				});
	}

	@Override
	public void add(PCLogOnInfoOfDaily domain) {
		this.commandProxy().insert(KrcdtDayPcLogonInfo.from(domain));
	}

	@Override
	public void remove(PCLogOnInfoOfDaily domain) {
		this.queryProxy()
				.find(new KrcdtDayPcLogonInfoPK(domain.getEmployeeId(), domain.getYmd()), KrcdtDayPcLogonInfo.class)
				.ifPresent(entity -> {
					this.commandProxy().remove(entity);
				});
	}

}
