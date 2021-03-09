package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveTimeRemainHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveTimeRemainingHistory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcdtAnnLeaTimeRemainHist;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcdtAnnLeaTimeRemainHistPK;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaAnnualLeaveTimeRemainHistRepository extends JpaRepository
		implements AnnualLeaveTimeRemainHistRepository {

	@Override
	public void addOrUpdate(AnnualLeaveTimeRemainingHistory domain) {
		KrcdtAnnLeaTimeRemainHistPK annLeaTimeRemainHistPK = new KrcdtAnnLeaTimeRemainHistPK(domain.getEmployeeId(), domain.getGrantProcessDate(), domain.getGrantDate());
		Optional<KrcdtAnnLeaTimeRemainHist> entityOpt = this.queryProxy().find(annLeaTimeRemainHistPK,
				KrcdtAnnLeaTimeRemainHist.class);
		if (entityOpt.isPresent()) {
			KrcdtAnnLeaTimeRemainHist entity = entityOpt.get();
			entity.cid = domain.getCid();
			entity.deadline = domain.getDeadline();
			entity.expStatus = domain.getExpirationStatus().value;
			entity.registerType = domain.getRegisterType().value;
			entity.grantDays = domain.getDetails().getGrantNumber().getDays().v();
			entity.grantMinutes = domain.getDetails().getGrantNumber().getMinutes().isPresent()
					? domain.getDetails().getGrantNumber().getMinutes().get().v() : null;
			entity.remainingDays = domain.getDetails().getRemainingNumber().getDays().v();
			entity.remaningMinutes = domain.getDetails().getRemainingNumber().getMinutes().isPresent()
					? domain.getDetails().getRemainingNumber().getMinutes().get().v() : null;
			entity.usedDays = domain.getDetails().getUsedNumber().getDays().v();
			entity.usedMinutes = domain.getDetails().getUsedNumber().getMinutes().isPresent()
					? domain.getDetails().getUsedNumber().getMinutes().get().v() : null;
			entity.stowageDays = domain.getDetails().getUsedNumber().getStowageDays().isPresent()
					? domain.getDetails().getUsedNumber().getStowageDays().get().v() : null;
			entity.usedPercent = domain.getDetails().getUsedPercent().v().doubleValue();
			if (domain.getAnnualLeaveConditionInfo().isPresent()) {
				entity.prescribedDays = domain.getAnnualLeaveConditionInfo().get().getPrescribedDays().v();
				entity.deductedDays = domain.getAnnualLeaveConditionInfo().get().getDeductedDays().v();
				entity.workingDays = domain.getAnnualLeaveConditionInfo().get().getWorkingDays().v();
			} else {
				entity.prescribedDays = null;
				entity.deductedDays = null;
				entity.workingDays = null;
			}
			this.commandProxy().update(entity);
		} else {
			this.commandProxy().insert(KrcdtAnnLeaTimeRemainHist.fromDomain(domain));
		}
	}

	@Override
	public List<AnnualLeaveTimeRemainingHistory> findByCalcDateClosureDate(String employeeId,
			GeneralDate calculationStartDate, GeneralDate closureStartDate) {
		String sql = "SELECT a FROM KrcdtAnnLeaTimeRemainHist a WHERE a.krcdtAnnLeaTimeRemainHistPK.grantProcessDate >= :calculationStartDate AND a.krcdtAnnLeaTimeRemainHistPK.grantProcessDate <= :closureStartDate AND a.sid = :employeeId";
		return this.queryProxy().query(sql, KrcdtAnnLeaTimeRemainHist.class)
				.setParameter("calculationStartDate", calculationStartDate)
				.setParameter("closureStartDate", closureStartDate).setParameter("employeeId", employeeId)
				.getList(item -> item.toDomain());
	}

	@Override
	public void deleteAfterDate(String employeeId, GeneralDate date) {
		String sql = "DELETE FROM KrcdtAnnLeaTimeRemainHist a WHERE a.krcdtAnnLeaTimeRemainHistPK.sid = :employeeId and a.krcdtAnnLeaTimeRemainHistPK.grantProcessDate > :startDate";
		this.getEntityManager().createQuery(sql).setParameter("employeeId", employeeId).setParameter("startDate", date);
	}

	@Override
	public List<AnnualLeaveTimeRemainingHistory> findBySid(String sid, GeneralDate ymd) {
		String sql = "SELECT a FROM KrcdtAnnLeaTimeRemainHist a"
				+ " WHERE a.krcdtAnnLeaTimeRemainHistPK.sid = :employeeId"
				+ " AND a.krcdtAnnLeaTimeRemainHistPK.grantProcessDate <= :ymd"
				+ " ORDER BY a.krcdtAnnLeaTimeRemainHistPK.grantDate asc";
		return this.queryProxy().query(sql, KrcdtAnnLeaTimeRemainHist.class)
				.setParameter("employeeId", sid)
				.setParameter("ymd", ymd)
				.getList(item -> item.toDomain());
	}

}
