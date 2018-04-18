package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.HalfdayAnnualLeaveMax;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.TimeAnnualLeaveMax;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.annlea.KrcmtAnnLeaMax;

@Stateless
public class JpaAnnLeaMaxDataRepo extends JpaRepository implements AnnLeaMaxDataRepository {

	@Override
	public Optional<AnnualLeaveMaxData> get(String employeeId) {
		Optional<KrcmtAnnLeaMax> entityOpt = this.queryProxy().find(employeeId, KrcmtAnnLeaMax.class);
		if (entityOpt.isPresent()) {
			KrcmtAnnLeaMax ent = entityOpt.get();
			return Optional.of(AnnualLeaveMaxData.createFromJavaType(ent.sid, ent.maxTimes, ent.usedTimes,
					ent.maxMinutes, ent.usedMinutes));
		}
		return Optional.empty();
	}

	@Override
	public void add(AnnualLeaveMaxData maxData) {
		KrcmtAnnLeaMax entity = new KrcmtAnnLeaMax();
		entity.sid = maxData.getEmployeeId();
		entity.cid = maxData.getCompanyId();
		if ( maxData.getHalfdayAnnualLeaveMax().isPresent()) {
			HalfdayAnnualLeaveMax halfday = maxData.getHalfdayAnnualLeaveMax().get();
			entity.maxTimes = halfday.getMaxTimes().v();
			entity.usedTimes = halfday.getUsedTimes().v();
			entity.remainingTimes = halfday.getRemainingTimes().v();
		}
		if ( maxData.getTimeAnnualLeaveMax().isPresent()) {
			TimeAnnualLeaveMax time = maxData.getTimeAnnualLeaveMax().get();
			entity.maxMinutes = time.getMaxMinutes().v();
			entity.usedMinutes = time.getUsedMinutes().v();
			entity.remainingMinutes = time.getRemainingMinutes().v();
		}
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(AnnualLeaveMaxData maxData) {
		Optional<KrcmtAnnLeaMax> entityOpt = this.queryProxy().find(maxData.getEmployeeId(), KrcmtAnnLeaMax.class);
		if (entityOpt.isPresent()) {
			KrcmtAnnLeaMax entity = entityOpt.get();
			if ( maxData.getHalfdayAnnualLeaveMax().isPresent()) {
				HalfdayAnnualLeaveMax halfday = maxData.getHalfdayAnnualLeaveMax().get();
				entity.maxTimes = halfday.getMaxTimes().v();
				entity.usedTimes = halfday.getUsedTimes().v();
				entity.remainingTimes = halfday.getRemainingTimes().v();
			} else {
				entity.maxTimes = null;
				entity.usedTimes = null;
				entity.remainingTimes = null;
			}
			if ( maxData.getTimeAnnualLeaveMax().isPresent()) {
				TimeAnnualLeaveMax time = maxData.getTimeAnnualLeaveMax().get();
				entity.maxMinutes = time.getMaxMinutes().v();
				entity.usedMinutes = time.getUsedMinutes().v();
				entity.remainingMinutes = time.getRemainingMinutes().v();
			} else {
				entity.maxMinutes = null;
				entity.usedMinutes = null;
				entity.remainingMinutes = null;
			}
			this.commandProxy().update(entity);
		} else {
			add(maxData);
		}
	}

	@Override
	public void delete(String employeeId) {
		Optional<KrcmtAnnLeaMax> entityOpt = this.queryProxy().find(employeeId, KrcmtAnnLeaMax.class);
		if (entityOpt.isPresent()) {
			KrcmtAnnLeaMax entity = entityOpt.get();
			this.commandProxy().remove(entity);
		}
	}

}
