package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainHistRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainingHistory;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.annlea.KrcdtAnnLeaRemainHist;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaAnnualLeaveRemainHistRepository extends JpaRepository implements AnnualLeaveRemainHistRepository {

	@Override
	public void add(AnnualLeaveRemainingHistory domain) {
		this.commandProxy().insert(KrcdtAnnLeaRemainHist.fromDomain(domain));
	}

}
