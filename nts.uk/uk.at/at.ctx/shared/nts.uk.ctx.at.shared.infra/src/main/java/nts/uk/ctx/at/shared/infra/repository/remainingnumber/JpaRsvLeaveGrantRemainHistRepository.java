package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RsvLeaveGrantRemainHistRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.resvlea.empinfo.grantremainingdata.KrcdtReserveLeaveRemainHist;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

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

	@Override
	public void delete(String employeeId, YearMonth ym, ClosureId closureId, ClosureDate closureDate) {
		String sql = "DELETE FROM KrcdtReserveLeaveRemainHist a WHERE a.sid = :employeeId and a.yearMonth = :ym AND a.closureId = :closureId AND a.closeDay = :closeDay AND a.isLastDay = :isLastDay";
		this.getEntityManager().createQuery(sql).setParameter("employeeId", employeeId).setParameter("ym", ym.v())
				.setParameter("closureId", closureId.value).setParameter("closeDay", closureDate.getClosureDay().v())
				.setParameter("isLastDay", closureDate.getLastDayOfMonth() ? 1 : 0);
	}

}
