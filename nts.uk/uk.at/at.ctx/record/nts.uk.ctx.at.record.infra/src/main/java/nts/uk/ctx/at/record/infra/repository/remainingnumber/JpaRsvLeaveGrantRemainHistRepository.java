package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainHistoryData;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RsvLeaveGrantRemainHistRepository;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.resvlea.empinfo.grantremainingdata.KrcdtReserveLeaveRemainHist;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaRsvLeaveGrantRemainHistRepository extends JpaRepository implements RsvLeaveGrantRemainHistRepository {

	@Override
	public void addOrUpdate(ReserveLeaveGrantRemainHistoryData domain, String cid) {
		Optional<KrcdtReserveLeaveRemainHist> entityOpt = this.queryProxy().find(domain.getRsvLeaID(),
				KrcdtReserveLeaveRemainHist.class);
		if (entityOpt.isPresent()) {
			KrcdtReserveLeaveRemainHist entity = entityOpt.get();
			entity.sid = domain.getEmployeeId();
			entity.cid = cid;
			entity.grantDate = domain.getGrantDate();
			entity.deadline = domain.getDeadline();
			entity.expStatus = domain.getExpirationStatus().value;
			entity.registerType = domain.getRegisterType().value;
			entity.grantDays = domain.getDetails().getGrantNumber().v();
			entity.usedDays = domain.getDetails().getUsedNumber().getDays().v();
			entity.overLimitDays = domain.getDetails().getUsedNumber().getOverLimitDays().isPresent()
					? domain.getDetails().getUsedNumber().getOverLimitDays().get().v() : null;
			entity.remainingDays = domain.getDetails().getRemainingNumber().v();
			entity.yearMonth = domain.getYearMonth().v();
			entity.closureId = domain.getClosureId().value;
			entity.closeDay = domain.getClosureDate().getClosureDay().v();
			entity.isLastDay = domain.getClosureDate().getLastDayOfMonth() ? 1 : 0;
			this.commandProxy().update(entity);
		} else
			this.commandProxy().insert(KrcdtReserveLeaveRemainHist.fromDomain(domain, cid));
	}

}
