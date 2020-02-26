package nts.uk.ctx.hr.develop.infra.repository.guidance;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.guidance.GuideMsgRepository;
import nts.uk.ctx.hr.develop.infra.entity.guidance.JogmtGuideMsg;

@Stateless
public class GuideMsgRepositoryImpl extends JpaRepository implements GuideMsgRepository {

	@Override
	public void updateGuideMsg(String id, String msg, boolean usageFlgByScreen) {
		Optional<JogmtGuideMsg> entity = this.queryProxy().find(id, JogmtGuideMsg.class);
		if (entity.isPresent()) {
			entity.get().guideMsg = msg;
			entity.get().usageFlgByScreen = usageFlgByScreen?1:0;
			this.commandProxy().update(entity.get());
		}
	}

}
