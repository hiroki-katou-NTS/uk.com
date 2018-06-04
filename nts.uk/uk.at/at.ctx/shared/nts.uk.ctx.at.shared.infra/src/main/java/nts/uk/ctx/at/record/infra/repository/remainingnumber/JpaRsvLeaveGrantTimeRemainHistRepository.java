package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantTimeRemainHistoryData;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RsvLeaveGrantTimeRemainHistRepository;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.resvlea.empinfo.grantremainingdata.KrcdtReserveLeaveTimeRemainHist;

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
