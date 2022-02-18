package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone.SettingsSmartphoneStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone.SettingsSmartphoneStampRepository;
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcmStampEreaLimitSyaPK;
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcmtStampEreaLimitSya;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcmtStampSmartPhone;

/**
 * スマホ打刻の打刻設定Repository
 * @author laitv
 *
 */
@Stateless
public class JpaSetSmartphoneStampRepository  extends JpaRepository implements SettingsSmartphoneStampRepository  {

	@Override
	public void insert(SettingsSmartphoneStamp settingsSmartphoneStamp) {
		this.commandProxy().insert(new KrcmtStampSmartPhone(settingsSmartphoneStamp));
	}

	@Override
	public void save(SettingsSmartphoneStamp settingsSmartphoneStamp) {
		this.getEntityManager().merge(new KrcmtStampSmartPhone(settingsSmartphoneStamp));
	}

	@Override
	public Optional<SettingsSmartphoneStamp> get(String cid, String sId) {
		Optional<KrcmtStampSmartPhone> entity = this.queryProxy().find(cid, KrcmtStampSmartPhone.class);
		if (entity.isPresent()) {
			
			SettingsSmartphoneStamp domain = entity.get().toDomain();
			
			this.queryProxy().find(new KrcmStampEreaLimitSyaPK(sId), KrcmtStampEreaLimitSya.class).ifPresent(area -> {
				domain.setStampingAreaRestriction(area.toDomain().getStampingAreaRestriction());
			});
			
			return Optional.of(domain);
			
		}
		return Optional.empty();
	}

}
