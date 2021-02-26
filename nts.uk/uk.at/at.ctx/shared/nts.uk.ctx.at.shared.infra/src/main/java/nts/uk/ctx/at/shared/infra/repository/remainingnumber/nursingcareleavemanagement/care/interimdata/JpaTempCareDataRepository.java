package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.care.interimdata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareDataRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.care.interimdata.KrcdtInterimCare;

@Stateless
public class JpaTempCareDataRepository extends JpaRepository implements TempCareDataRepository {

	private static final String QUERY_WITH_PERIOD = "SELECT c FROM KrcdtInterimCare c "
			+ " WHERE c.id.sid =:employeeId AND c.id.ymd >= :startDate AND c.id.ymd <= :endDate";

	@Override
	public List<TempCareData> findByEmpIdInPeriod(String employeeId, GeneralDate startDate, GeneralDate endDate) {
		List<KrcdtInterimCare> entities = this.queryProxy().query(QUERY_WITH_PERIOD, KrcdtInterimCare.class)
				.setParameter("employeeId", employeeId).setParameter("startDate", startDate)
				.setParameter("endDate", endDate).getList();
		return entities.stream().map(ent -> ent.toDomain()).collect(Collectors.toList());
	}

}
