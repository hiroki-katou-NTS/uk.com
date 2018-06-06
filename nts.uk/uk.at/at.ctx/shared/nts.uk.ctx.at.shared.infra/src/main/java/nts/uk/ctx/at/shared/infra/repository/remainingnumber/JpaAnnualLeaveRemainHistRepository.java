package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainingHistory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcdtAnnLeaRemainHist;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaAnnualLeaveRemainHistRepository extends JpaRepository implements AnnualLeaveRemainHistRepository {

	@Override
	public void addOrUpdate(AnnualLeaveRemainingHistory domain) {
		Optional<KrcdtAnnLeaRemainHist> opt = this.queryProxy().find(domain.getAnnLeavID(),
				KrcdtAnnLeaRemainHist.class);
		if (opt.isPresent()) {
			KrcdtAnnLeaRemainHist entity = opt.get();
			entity.cid = domain.getCid();
			entity.sid = domain.getEmployeeId();
			entity.yearMonth = domain.getYearMonth().v();
			entity.closureId = domain.getClosureId().value;
			entity.closeDay = domain.getClosureDate().getClosureDay().v();
			entity.isLastDay = domain.getClosureDate().getLastDayOfMonth() ? 1 : 0;
			entity.grantDate = domain.getGrantDate();
			entity.deadline = domain.getDeadline();
			entity.expStatus = domain.getExpirationStatus().value;
			entity.registerType = domain.getRegisterType().value;
			entity.deductedDays = domain.getAnnualLeaveConditionInfo().isPresent()
					? domain.getAnnualLeaveConditionInfo().get().getDeductedDays().v() : null;
			entity.prescribedDays = domain.getAnnualLeaveConditionInfo().isPresent()
					? domain.getAnnualLeaveConditionInfo().get().getPrescribedDays().v() : null;
			entity.workingDays = domain.getAnnualLeaveConditionInfo().isPresent()
					? domain.getAnnualLeaveConditionInfo().get().getWorkingDays().v() : null;
			entity.remainingDays = domain.getDetails().getRemainingNumber().getDays().v();
			entity.remaningMinutes = domain.getDetails().getRemainingNumber().getMinutes().isPresent()
					? domain.getDetails().getRemainingNumber().getMinutes().get().v() : null;
			entity.grantDays = domain.getDetails().getGrantNumber().getDays().v();
			entity.grantMinutes = domain.getDetails().getGrantNumber().getMinutes().isPresent()
					? domain.getDetails().getGrantNumber().getMinutes().get().v() : null;
			entity.stowageDays = domain.getDetails().getUsedNumber().getStowageDays().isPresent()
					? domain.getDetails().getUsedNumber().getStowageDays().get().v() : null;
			entity.usedDays = domain.getDetails().getUsedNumber().getDays().v();
			entity.usedMinutes = domain.getDetails().getUsedNumber().getMinutes().isPresent()
					? domain.getDetails().getUsedNumber().getMinutes().get().v() : null;
			entity.usedPercent = domain.getDetails().getUsedPercent().v().doubleValue();
			this.commandProxy().update(entity);
		} else
			this.commandProxy().insert(KrcdtAnnLeaRemainHist.fromDomain(domain));
	}

}
