package nts.uk.ctx.at.record.infra.repository.remainingnumber;

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
	public void add(ReserveLeaveGrantRemainHistoryData domain, String cid) {
		this.commandProxy().insert(KrcdtReserveLeaveRemainHist.fromDomain(domain, cid));
	}

}
