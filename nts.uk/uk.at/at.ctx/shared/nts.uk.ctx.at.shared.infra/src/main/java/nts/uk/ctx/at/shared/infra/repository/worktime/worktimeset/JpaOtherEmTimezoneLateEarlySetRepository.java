package nts.uk.ctx.at.shared.infra.repository.worktime.worktimeset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.OtherEmTimezoneLateEarlySetRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtOtherLateEarly;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtOtherLateEarlyPK;

@Stateless
public class JpaOtherEmTimezoneLateEarlySetRepository extends JpaRepository
		implements OtherEmTimezoneLateEarlySetRepository {

	@Override
	public boolean getStampExaclyByKey(String cid, String worktimeCd, int workFormAtr, int worktimeSetMethod,
			int lateEarlyAtr) {

		KshmtOtherLateEarlyPK pk = new KshmtOtherLateEarlyPK();
		pk.setCid(cid);
		pk.setLateEarlyAtr(lateEarlyAtr);
		pk.setWorkFormAtr(workFormAtr);
		pk.setWorktimeCd(worktimeCd);
		pk.setWorktimeSetMethod(worktimeSetMethod);
		;
		Optional<KshmtOtherLateEarly> entity = this.queryProxy().find(pk, KshmtOtherLateEarly.class);
		if (!entity.isPresent())
			return false;
		return entity.map(x -> x.getExtractLateEarlyTime() == 1).orElse(false);

	}

}
