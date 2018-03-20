package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveNumberInfo;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.resvlea.KrcmtReverseLeaRemain;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.resvlea.KrcmtReverseLeaRemainPK;

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

	@Override
	public void add(ReserveLeaveGrantRemainingData data) {
		KrcmtReverseLeaRemain e = new KrcmtReverseLeaRemain();
		e.key.employeeId = data.getEmployeeId();
		e.key.grantDate = data.getGrantDate();
		updateDetail(e, data);
		this.commandProxy().update(e);
	}

	private void updateDetail(KrcmtReverseLeaRemain e, ReserveLeaveGrantRemainingData data) {
		e.deadline = data.getDeadline();
		e.expStatus = data.getExpirationStatus().value;
		e.registerType = data.getRegisterType().value;
		ReserveLeaveNumberInfo details = data.getDetails();
		e.grantDays = details.getGrantNumber().v();
		e.usedDays = details.getUsedNumber().getDays().v();
		e.overLimitDays = details.getUsedNumber().getOverLimitDays().isPresent()
				? details.getUsedNumber().getOverLimitDays().get().v() : null;
		e.remainingDays = details.getRemainingNumber().v();
	}

	@Override
	public void update(ReserveLeaveGrantRemainingData data) {
		Optional<KrcmtReverseLeaRemain> entityOpt = this.queryProxy().find(
				new KrcmtReverseLeaRemainPK(data.getEmployeeId(), data.getGrantDate()), KrcmtReverseLeaRemain.class);
		if (entityOpt.isPresent()) {
			updateDetail(entityOpt.get(), data);
			this.commandProxy().update(entityOpt.get());
		}
	}

	@Override
	public void delete(String employeeId, GeneralDate grantDate) {
		Optional<KrcmtReverseLeaRemain> entityOpt = this.queryProxy()
				.find(new KrcmtReverseLeaRemainPK(employeeId, grantDate), KrcmtReverseLeaRemain.class);
		if (entityOpt.isPresent()) {
			this.commandProxy().remove(entityOpt.get());
		}
	}

}
