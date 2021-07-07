package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopier;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopierRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.KrcmtStampRicoh;

/**
 * @author ThanhPV
 * RICOH複写機の打刻設定Repository
 */
@Stateless
public class JpaStampSettingOfRICOHCopierRepositoryImpl  extends JpaRepository implements StampSettingOfRICOHCopierRepository  {

	@Override
	public void insert(StampSettingOfRICOHCopier settingsSmartphoneStamp) {
		this.commandProxy().insert(new KrcmtStampRicoh(settingsSmartphoneStamp));
	}

	@Override
	public void update(StampSettingOfRICOHCopier settingsSmartphoneStamp) {
		this.commandProxy().update(new KrcmtStampRicoh(settingsSmartphoneStamp));
	}
	
	@Override
	public Optional<StampSettingOfRICOHCopier> get(String cid) {
		Optional<KrcmtStampRicoh> entity = this.queryProxy().find(cid, KrcmtStampRicoh.class);
		return entity.map(c->c.toDomain());
	}
}
