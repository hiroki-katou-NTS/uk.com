package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.KrcmtReverseLeaRemain;

@Stateless
public class JpaRervLeaGrantRemDataRepo extends JpaRepository implements RervLeaGrantRemDataRepository {

	private String QUERY_WITH_EMP_ID = "SELECT a FROM KrcmtReverseLeaRemain a WHERE a.key.employeeId = :employeeId";

	@Override
	public List<ReserveLeaveGrantRemainingData> find(String employeeId) {
		List<KrcmtReverseLeaRemain> entities = this.queryProxy().query(QUERY_WITH_EMP_ID, KrcmtReverseLeaRemain.class)
				.setParameter("employeeId", employeeId).getList();
		return entities.stream()
				.map(ent -> ReserveLeaveGrantRemainingData.createFromJavaType(ent.key.employeeId, ent.key.grantDate,
						ent.deadline, ent.expStatus, ent.registerType, ent.grantDays, ent.usedDays, ent.overLimitDays,
						ent.remainingDays))
				.collect(Collectors.toList());
	}

}
