package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPUnitUseSettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BPUnitUseSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class BPUnitUseSettingFinder {
	@Inject
	private BPUnitUseSettingRepository bpUnitUseSettingRepository;

	public BPUnitUseSettingDto getSetting() {
		String companyId = AppContexts.user().companyId();
		Optional<BPUnitUseSetting> bpUnitUseSetting = bpUnitUseSettingRepository.getSetting(companyId);
		return this.toBPUnitUseSettingDto(bpUnitUseSetting.get());
	}

	private BPUnitUseSettingDto toBPUnitUseSettingDto(BPUnitUseSetting bpUnitUseSetting) {
		return new BPUnitUseSettingDto(bpUnitUseSetting.getCompanyId().toString(),
				bpUnitUseSetting.getWorkplaceUseAtr().value, bpUnitUseSetting.getPersonalUseAtr().value,
				bpUnitUseSetting.getWorkingTimesheetUseAtr().value);
	}

}
