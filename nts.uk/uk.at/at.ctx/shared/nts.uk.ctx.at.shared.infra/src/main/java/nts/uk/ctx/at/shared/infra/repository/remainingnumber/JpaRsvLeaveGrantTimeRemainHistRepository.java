package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantTimeRemainHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RsvLeaveGrantTimeRemainHistRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.resvlea.empinfo.grantremainingdata.KrcdtReserveLeaveTimeRemainHist;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaRsvLeaveGrantTimeRemainHistRepository extends JpaRepository
		implements RsvLeaveGrantTimeRemainHistRepository {

	@Override
	public void addOrUpdate(ReserveLeaveGrantTimeRemainHistoryData domain, String cid) {
		Optional<KrcdtReserveLeaveTimeRemainHist> entityOpt = this.queryProxy().find(domain.getRsvLeaID(),
				KrcdtReserveLeaveTimeRemainHist.class);
		if (entityOpt.isPresent()) {
			KrcdtReserveLeaveTimeRemainHist entity = entityOpt.get();
			entity.sid = domain.getEmployeeId();
			entity.cid = cid;
			entity.grantDate = domain.getGrantDate();
			entity.deadline = domain.getDeadline();
			entity.expStatus = domain.getExpirationStatus().value;
			entity.registerType = domain.getRegisterType().value;
			entity.grantDays = domain.getDetails().getGrantNumber().v();
			entity.remainingDays = domain.getDetails().getRemainingNumber().v();
			entity.usedDays = domain.getDetails().getUsedNumber().getDays().v();
			entity.overLimitDays = domain.getDetails().getUsedNumber().getOverLimitDays().isPresent()
					? domain.getDetails().getUsedNumber().getOverLimitDays().get().v() : null;
			entity.grantProcessDate = domain.getGrantProcessDate();
			this.commandProxy().update(entity);
		} else {
			this.commandProxy().insert(KrcdtReserveLeaveTimeRemainHist.fromDomain(domain, cid));
		}
	}

	@Override
	public void deleteAfterDate(String employeeId, GeneralDate date) {
		String sql = "DELETE FROM KrcdtReserveLeaveTimeRemainHist a WHERE a.sid = :employeeId and a.grantProcessDate > :startDate";
		this.getEntityManager().createQuery(sql).setParameter("employeeId", employeeId).setParameter("startDate", date);
	}

}
