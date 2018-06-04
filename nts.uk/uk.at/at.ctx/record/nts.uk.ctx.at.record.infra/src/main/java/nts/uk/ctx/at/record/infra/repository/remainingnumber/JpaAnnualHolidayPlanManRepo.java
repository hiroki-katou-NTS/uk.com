package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.comfirmdata.AnnualHolidayPlanManRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.comfirmdata.AnnualHolidayPlanMana;
import nts.uk.ctx.at.record.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.annlea.KrcdtAnnualPlanMana;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;

public class JpaAnnualHolidayPlanManRepo extends JpaRepository implements AnnualHolidayPlanManRepository{
	private String QUERY_BY_SID_WORKTYPE_PERIOD = "SELECT c FROM KrcdtAnnualPlanMana c"
			+ " WHERE c.pk.sId = :employeeId AND c.pk.workTypeCd = :workTypeCd "
			+ " AND c.pk.ymd >= :startDate "
			+ " AND c.pk.ymd <= :endDate"
			+ " ORDER BY c.pk.ymd";
	@Override
	public List<AnnualHolidayPlanMana> getDataBySidWorkTypePeriod(String employeeid, String workTypeCd,
			Period dateData) {
		List<KrcdtAnnualPlanMana> entities = this.queryProxy().query(QUERY_BY_SID_WORKTYPE_PERIOD, KrcdtAnnualPlanMana.class)
				.setParameter("employeeId", employeeid)
				.setParameter("workTypeCd", workTypeCd)
				.setParameter("startDate", dateData.getStartDate())
				.setParameter("endDate", dateData.getEndDate())
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
