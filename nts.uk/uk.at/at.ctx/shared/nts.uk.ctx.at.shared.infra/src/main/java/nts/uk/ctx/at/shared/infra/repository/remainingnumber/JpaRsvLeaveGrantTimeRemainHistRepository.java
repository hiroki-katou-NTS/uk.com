package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantTimeRemainHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RsvLeaveGrantTimeRemainHistRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.resvlea.empinfo.grantremainingdata.KrcdtReserveLeaveTimeRemainHist;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.resvlea.empinfo.grantremainingdata.KrcdtReserveLeaveTimeRemainHistPK;

/**
 *
 * @author HungTT
 *
 */

@Stateless
public class JpaRsvLeaveGrantTimeRemainHistRepository extends JpaRepository implements RsvLeaveGrantTimeRemainHistRepository {


	@Override
	public void addOrUpdate(ReserveLeaveGrantTimeRemainHistoryData domain) {
		KrcdtReserveLeaveTimeRemainHistPK leaveTimeRemainHistPK = new KrcdtReserveLeaveTimeRemainHistPK(domain.getEmployeeId(), domain.getGrantProcessDate(), domain.getGrantDate());
		Optional<KrcdtReserveLeaveTimeRemainHist> entityOpt = this.queryProxy().find(leaveTimeRemainHistPK,
				KrcdtReserveLeaveTimeRemainHist.class);
		if (entityOpt.isPresent()) {
			KrcdtReserveLeaveTimeRemainHist entity = entityOpt.get();
			entity.deadline = domain.getDeadline();
			entity.expStatus = domain.getExpirationStatus().value;
			entity.registerType = domain.getRegisterType().value;
			entity.grantDays = domain.getDetails().getGrantNumber().getDays().v();
			entity.remainingDays = domain.getDetails().getRemainingNumber().getDays().v();
			entity.usedDays = domain.getDetails().getUsedNumber().getDays().v();
			entity.overLimitDays = domain.getDetails().getUsedNumber().getLeaveOverLimitNumber().isPresent()
					? domain.getDetails().getUsedNumber().getLeaveOverLimitNumber().get().numberOverDays.v() : null;
			this.commandProxy().update(entity);
		} else {
			this.commandProxy().insert(KrcdtReserveLeaveTimeRemainHist.fromDomain(domain));
		}
	}

}
