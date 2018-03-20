package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveConditionInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveNumberInfo;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.annlea.KRcmtAnnLeaRemain;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.annlea.KRcmtAnnLeaRemainPK;

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

	@Override
	public void add(AnnualLeaveGrantRemainingData data) {
		KRcmtAnnLeaRemain entity = new KRcmtAnnLeaRemain();
		entity.key.employeeId = data.getEmployeeId();
		entity.key.grantDate = data.getGrantDate();
		updateValue(entity, data);

		this.commandProxy().insert(entity);
	}

	@Override
	public void update(AnnualLeaveGrantRemainingData data) {
		Optional<KRcmtAnnLeaRemain> entityOpt = this.queryProxy()
				.find(new KRcmtAnnLeaRemainPK(data.getEmployeeId(), data.getGrantDate()), KRcmtAnnLeaRemain.class);
		if (entityOpt.isPresent()) {
			KRcmtAnnLeaRemain entity = entityOpt.get();
			updateValue(entity, data);

			this.commandProxy().update(entity);
		}

	}

	private void updateValue(KRcmtAnnLeaRemain entity, AnnualLeaveGrantRemainingData data) {
		entity.deadline = data.getDeadline();
		entity.expStatus = data.getExpirationStatus().value;
		entity.registerType = data.getRegisterType().value;

		AnnualLeaveNumberInfo details = data.getDetails();

		// grant data
		entity.grantDays = details.getGrantNumber().getDays().v();
		entity.grantMinutes = details.getGrantNumber().getMinutes().isPresent()
				? details.getGrantNumber().getMinutes().get().v() : null;
		// used data
		entity.usedDays = details.getUsedNumber().getDays().v();
		entity.usedMinutes = details.getUsedNumber().getMinutes().isPresent()
				? details.getUsedNumber().getMinutes().get().v() : null;
		entity.stowageDays = details.getUsedNumber().getStowageDays().isPresent()
				? details.getUsedNumber().getStowageDays().get().v() : null;
		// remain data
		entity.remainingDays = details.getRemainingNumber().getDays().v();
		entity.remaningMinutes = details.getRemainingNumber().getMinutes().isPresent()
				? details.getRemainingNumber().getMinutes().get().v() : null;

		entity.usedPercent = details.getUsedPercent().v().doubleValue();

		if (data.getAnnualLeaveConditionInfo().isPresent()) {
			AnnualLeaveConditionInfo conditionInfo = data.getAnnualLeaveConditionInfo().get();
			entity.perscribedDays = conditionInfo.getDeductedDays().v();
			entity.deductedDays = conditionInfo.getDeductedDays().v();
			entity.workingDays = conditionInfo.getWorkingDays().v();
		} else {
			entity.perscribedDays = null;
			entity.deductedDays = null;
			entity.workingDays = null;
		}
	}

	@Override
	public void delete(String employeeId, GeneralDate grantDate) {
		Optional<KRcmtAnnLeaRemain> dataOpt = this.queryProxy().find(new KRcmtAnnLeaRemainPK(employeeId, grantDate),
				KRcmtAnnLeaRemain.class);
		if (dataOpt.isPresent()) {
			this.commandProxy().remove(dataOpt.get());
		}

	}

}
