package nts.uk.ctx.at.record.infra.repository.daily.ouen;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.record.infra.entity.daily.ouen.KrcdtDayOuenTimeSheet;

@Stateless
public class OuenWorkTimeSheetOfDailyRepoImpl extends JpaRepository implements OuenWorkTimeSheetOfDailyRepo {

	@Override
	public List<OuenWorkTimeSheetOfDaily> find(String empId, GeneralDate ymd) {
		return queryProxy().query("SELECT o FROM KrcdtDayOuenTimeSheet o WHERE o.pk.sid = :sid AND o.ok.ymd = :ymd", 
									KrcdtDayOuenTimeSheet.class)
				.setParameter("sid", empId).setParameter("ymd", ymd)
				.getList(e -> e.domain());
	}

	@Override
	public void update(List<OuenWorkTimeSheetOfDaily> domain) {
		domain.stream().map(c -> KrcdtDayOuenTimeSheet.convert(c)).forEach(e -> {
			commandProxy().update(e);
		});
	}

	@Override
	public void insert(List<OuenWorkTimeSheetOfDaily> domain) {
		domain.stream().map(c -> KrcdtDayOuenTimeSheet.convert(c)).forEach(e -> {
			commandProxy().insert(e);
		});
	}

	@Override
	public void delete(List<OuenWorkTimeSheetOfDaily> domain) {
		domain.stream().map(c -> KrcdtDayOuenTimeSheet.convert(c)).forEach(e -> {
			commandProxy().remove(e);
		});
	}

	@Override
	public List<OuenWorkTimeSheetOfDaily> findByDatePeriod(String empId, DatePeriod datePeriod) {
		return queryProxy().query("SELECT o FROM KrcdtDayOuenTimeSheet o WHERE o.pk.sid = :sid AND o.pk.ymd >= :str AND o.pk.ymd <= :end", 
				KrcdtDayOuenTimeSheet.class)
				.setParameter("sid", empId).setParameter("str", datePeriod.start()).setParameter("end", datePeriod.end())
				.getList(e -> e.domain());
	}

}
