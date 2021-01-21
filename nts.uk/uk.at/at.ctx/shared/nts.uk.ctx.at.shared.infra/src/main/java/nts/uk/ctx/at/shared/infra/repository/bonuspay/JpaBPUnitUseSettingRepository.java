package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPUnitUseSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BPUnitUseSetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstBPUnitUseSetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstBPUnitUseSettingPK;

@Stateless
public class JpaBPUnitUseSettingRepository extends JpaRepository implements BPUnitUseSettingRepository {
	private static final String SELECT_BY_COMPANYID = "SELECT c FROM KbpstBPUnitUseSetting c WHERE c.kbpstBPUnitUseSettingPK.companyId = :companyId";

	@Override
	public void updateSetting(BPUnitUseSetting setting) {
		Optional<KbpstBPUnitUseSetting> kbpstBPUnitUseSettingOptional = this.queryProxy().find(new KbpstBPUnitUseSettingPK(setting.getCompanyId().toString()), KbpstBPUnitUseSetting.class);
		if(kbpstBPUnitUseSettingOptional.isPresent()){
			KbpstBPUnitUseSetting kbpstBPUnitUseSetting = kbpstBPUnitUseSettingOptional.get();
			kbpstBPUnitUseSetting.personalUseAtr=new BigDecimal(setting.getPersonalUseAtr().value);
			kbpstBPUnitUseSetting.workingTimesheetUseAtr=new BigDecimal(setting.getWorkingTimesheetUseAtr().value);
			kbpstBPUnitUseSetting.workplaceUseAtr=new BigDecimal(setting.getWorkplaceUseAtr().value);
			this.commandProxy().update(kbpstBPUnitUseSetting);
		}
	}
	
	@Override
	public Optional<BPUnitUseSetting> getSetting(String companyId) {
		Optional<KbpstBPUnitUseSetting> kbpstBPUnitUseSetting = this.queryProxy()
				.query(SELECT_BY_COMPANYID, KbpstBPUnitUseSetting.class).setParameter("companyId", companyId)
				.getSingle();
		if (kbpstBPUnitUseSetting.isPresent()) {
			return Optional.ofNullable(this.toUnitUseSettingDomain(kbpstBPUnitUseSetting.get()));
		}
		return Optional.empty();

	}

	private KbpstBPUnitUseSetting toUnitUseSettingEntity(BPUnitUseSetting bPUnitUseSetting) {
		return new KbpstBPUnitUseSetting(new KbpstBPUnitUseSettingPK(bPUnitUseSetting.getCompanyId().toString()),
				new BigDecimal(bPUnitUseSetting.getWorkplaceUseAtr().value),
				new BigDecimal(bPUnitUseSetting.getPersonalUseAtr().value),
				new BigDecimal(bPUnitUseSetting.getWorkingTimesheetUseAtr().value));
	}

	@Override
	public void insertSetting(BPUnitUseSetting setting) {
		this.commandProxy().insert(this.toUnitUseSettingEntity(setting));
	}

	private BPUnitUseSetting toUnitUseSettingDomain(KbpstBPUnitUseSetting kbpstBPUnitUseSetting) {
		return BPUnitUseSetting.createFromJavaType(kbpstBPUnitUseSetting.kbpstBPUnitUseSettingPK.companyId,
				kbpstBPUnitUseSetting.workplaceUseAtr.intValue(), kbpstBPUnitUseSetting.personalUseAtr.intValue(),
				kbpstBPUnitUseSetting.workingTimesheetUseAtr.intValue());
	}

}
