package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
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
	public void add(ReserveLeaveGrantTimeRemainHistoryData domain, String cid) {
		this.commandProxy().insert(KrcdtReserveLeaveTimeRemainHist.fromDomain(domain, cid));
	}

}
