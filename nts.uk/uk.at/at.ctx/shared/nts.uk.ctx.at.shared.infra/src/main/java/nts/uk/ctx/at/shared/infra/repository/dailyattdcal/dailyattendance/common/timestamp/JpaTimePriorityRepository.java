package nts.uk.ctx.at.shared.infra.repository.dailyattdcal.dailyattendance.common.timestamp;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimePriority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimePriorityRepository;
import nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.common.timestamp.KrcmtTimePriority;

@Stateless
public class JpaTimePriorityRepository extends JpaRepository implements TimePriorityRepository {

	@Override
	public Optional<TimePriority> getByCid(String cid) {
		Optional<KrcmtTimePriority> entity = this.queryProxy().find(cid, KrcmtTimePriority.class);
		if(entity.isPresent()) {
			return Optional.of(entity.get().todomain());
		}
		return Optional.empty();
	}

}
