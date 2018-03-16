package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.KrcmtAnnLeaMax;

@Stateless
public class JpaAnnLeaMaxDataRepo extends JpaRepository implements AnnLeaMaxDataRepository {

	@Override
	public Optional<AnnualLeaveMaxData> get(String employeId) {
		Optional<KrcmtAnnLeaMax> entityOpt = this.queryProxy().find(employeId, KrcmtAnnLeaMax.class);
		if (entityOpt.isPresent()) {
			KrcmtAnnLeaMax ent = entityOpt.get();
			return Optional.of(AnnualLeaveMaxData.createFromJavaType(ent.employeeId, ent.maxTimes, ent.usedTimes,
					ent.remainingTimes, ent.maxMinutes, ent.usedMinutes, ent.remainingMinutes));
		}
		return Optional.empty();
	}

}
