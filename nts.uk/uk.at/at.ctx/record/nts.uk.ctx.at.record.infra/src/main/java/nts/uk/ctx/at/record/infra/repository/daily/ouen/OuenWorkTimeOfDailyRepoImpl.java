package nts.uk.ctx.at.record.infra.repository.daily.ouen;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDailyRepo;
import nts.uk.ctx.at.record.infra.entity.daily.ouen.KrcdtDayOuenTime;

@Stateless
public class OuenWorkTimeOfDailyRepoImpl extends JpaRepository implements OuenWorkTimeOfDailyRepo {

	@Override
	public List<OuenWorkTimeOfDaily> find(String empId, GeneralDate ymd) {
		return queryProxy().query("SELECT o FROM KrcdtDayOuenTime o WHERE o.pk.sid = :sid AND o.ok.ymd = :ymd", 
									KrcdtDayOuenTime.class)
				.setParameter("sid", empId).setParameter("ymd", ymd)
				.getList(e -> e.domain());
	}

	@Override
	public void update(List<OuenWorkTimeOfDaily> domain) {
		domain.stream().map(c -> KrcdtDayOuenTime.convert(c)).forEach(e -> {
			commandProxy().update(e);
		});
	}

	@Override
	public void insert(List<OuenWorkTimeOfDaily> domain) {
		domain.stream().map(c -> KrcdtDayOuenTime.convert(c)).forEach(e -> {
			commandProxy().insert(e);
		});
	}

	@Override
	public void delete(List<OuenWorkTimeOfDaily> domain) {
		domain.stream().map(c -> KrcdtDayOuenTime.convert(c)).forEach(e -> {
			commandProxy().remove(e);
		});
	}

}
