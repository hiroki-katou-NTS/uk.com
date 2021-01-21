package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPUnitUseSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BPUnitUseSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BPUnitUseSettingFinder {
	@Inject
	private BPUnitUseSettingRepository bpUnitUseSettingRepository;

	public BPUnitUseSettingDto getSetting() {
		String companyId = AppContexts.user().companyId();
		Optional<BPUnitUseSetting> bpUnitUseSetting = bpUnitUseSettingRepository.getSetting(companyId);
		if(bpUnitUseSetting.isPresent()){
			return this.toBPUnitUseSettingDto(bpUnitUseSetting.get());
		}
		return null;
		
	}

	private BPUnitUseSettingDto toBPUnitUseSettingDto(BPUnitUseSetting bpUnitUseSetting) {
		return new BPUnitUseSettingDto(bpUnitUseSetting.getCompanyId().toString(),
				bpUnitUseSetting.getWorkplaceUseAtr().value, bpUnitUseSetting.getPersonalUseAtr().value,
				bpUnitUseSetting.getWorkingTimesheetUseAtr().value);
	}

}
