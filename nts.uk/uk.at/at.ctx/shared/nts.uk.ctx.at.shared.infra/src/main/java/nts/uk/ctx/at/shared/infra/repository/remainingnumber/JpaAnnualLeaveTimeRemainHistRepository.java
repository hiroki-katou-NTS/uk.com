package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveTimeRemainHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveTimeRemainingHistory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcdtAnnLeaTimeRemainHist;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaAnnualLeaveTimeRemainHistRepository extends JpaRepository
		implements AnnualLeaveTimeRemainHistRepository {

	@Override
	public void add(AnnualLeaveTimeRemainingHistory domain) {
		this.commandProxy().insert(KrcdtAnnLeaTimeRemainHist.fromDomain(domain));
	}

	@Override
	public List<AnnualLeaveTimeRemainingHistory> findByCalcDateClosureDate(GeneralDate calculationStartDate,
			GeneralDate closureStartDate) {
		String sql = "SELECT a FROM KrcdtAnnLeaTimeRemainHist a WHERE a.grantProcessDate >= :calculationStartDate AND a.grantProcessDate <= :closureStartDate";
		return this.queryProxy().query(sql, KrcdtAnnLeaTimeRemainHist.class)
				.setParameter("calculationStartDate", calculationStartDate)
				.setParameter("closureStartDate", closureStartDate).getList(item -> item.toDomain());
	}

}
