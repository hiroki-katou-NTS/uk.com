package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.comfirmdata.AnnualHolidayPlanManRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.comfirmdata.AnnualHolidayPlanMana;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcdtAnnualPlanMana;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaAnnualHolidayPlanManRepo extends JpaRepository implements AnnualHolidayPlanManRepository{
	private static final String QUERY_BY_SID_WORKTYPE_PERIOD = "SELECT c FROM KrcdtAnnualPlanMana c"
			+ " WHERE c.pk.sId = :employeeId AND c.pk.workTypeCd = :workTypeCd "
			+ " AND c.pk.ymd >= :startDate "
			+ " AND c.pk.ymd <= :endDate"
			+ " ORDER BY c.pk.ymd";
	@Override
	public List<AnnualHolidayPlanMana> getDataBySidWorkTypePeriod(String employeeid, String workTypeCd,
			DatePeriod dateData) {
		List<KrcdtAnnualPlanMana> entities = this.queryProxy().query(QUERY_BY_SID_WORKTYPE_PERIOD, KrcdtAnnualPlanMana.class)
				.setParameter("employeeId", employeeid)
				.setParameter("workTypeCd", workTypeCd)
				.setParameter("startDate", dateData.start())
				.setParameter("endDate", dateData.end())
				.getList();
		return entities.stream().map(ent -> toDomain(ent)).collect(Collectors.toList());
	}
	private AnnualHolidayPlanMana toDomain(KrcdtAnnualPlanMana ent) {		
		return new AnnualHolidayPlanMana(ent.pk.sId,
				ent.pk.workTypeCd,
				ent.pk.ymd,
				new ManagementDays(ent.useDays));
	}

}
