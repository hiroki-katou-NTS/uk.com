package nts.uk.ctx.hr.develop.infra.repository.guidance;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.guidance.Guidance;
import nts.uk.ctx.hr.develop.dom.guidance.GuidanceRepository;
import nts.uk.ctx.hr.develop.infra.entity.guidance.JogmtGuideDispSetting;

@Stateless
public class GuidanceRepositoryImpl extends JpaRepository implements GuidanceRepository{

	@Override
	public Optional<Guidance> getGuidance(String companyId) {
		Optional<JogmtGuideDispSetting> entity = this.queryProxy().find(companyId, JogmtGuideDispSetting.class);
		if(entity.isPresent()) {
			return Optional.of(entity.get().toDomain());
		}else {
			return Optional.empty();
		}
	}

	@Override
	public void addGuidance(Guidance domain) {
		this.commandProxy().insert(new JogmtGuideDispSetting(domain));
	}

	@Override
	public void updateGuidance(Guidance domain) {
		this.commandProxy().update(new JogmtGuideDispSetting(domain));
	}

}
