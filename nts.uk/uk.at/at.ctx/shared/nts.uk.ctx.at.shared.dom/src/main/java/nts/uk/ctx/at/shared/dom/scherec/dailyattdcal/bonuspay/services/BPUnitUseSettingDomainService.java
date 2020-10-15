package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.services;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPUnitUseSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BPUnitUseSetting;



@Stateless
public class BPUnitUseSettingDomainService implements BPUnitUseSettingService {
	@Inject
	private BPUnitUseSettingRepository pbUnitUseSettingRepository;

	@Override
	public void updateSetting(BPUnitUseSetting setting) {
		Optional<BPUnitUseSetting> pbUnitUseSetting = pbUnitUseSettingRepository
				.getSetting(String.valueOf(setting.getCompanyId()));
		if (pbUnitUseSetting.isPresent()) {
			pbUnitUseSettingRepository.updateSetting(setting);
		} else {
			pbUnitUseSettingRepository.insertSetting(setting);
		}
	}

}
