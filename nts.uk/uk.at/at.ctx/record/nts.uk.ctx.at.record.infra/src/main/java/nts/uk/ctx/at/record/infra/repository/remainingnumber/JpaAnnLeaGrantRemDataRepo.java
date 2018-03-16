package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.KRcmtAnnLeaRemain;

@Stateless
public class JpaAnnLeaGrantRemDataRepo extends JpaRepository implements AnnLeaGrantRemDataRepository {

	private String QUERY_WITH_EMP_ID = "SELECT a FROM KRcmtAnnLeaRemain a WHERE a.key.employeeId = :employeeId";

	@Override
	public List<AnnualLeaveGrantRemainingData> find(String employeeId) {
		List<KRcmtAnnLeaRemain> entities = this.queryProxy().query(QUERY_WITH_EMP_ID, KRcmtAnnLeaRemain.class)
				.setParameter("employeeId", employeeId).getList();
		return entities.stream()
				.map(ent -> AnnualLeaveGrantRemainingData.createFromJavaType(ent.key.employeeId, ent.key.grantDate,
						ent.deadline, ent.expStatus, ent.registerType, ent.grantDays, ent.grantMinutes, ent.usedDays,
						ent.usedMinutes, ent.stowageDays, ent.remainingDays, ent.remaningMinutes, ent.usedPercent,
						ent.perscribedDays, ent.deductedDays, ent.workingDays))
				.collect(Collectors.toList());
	}

}
