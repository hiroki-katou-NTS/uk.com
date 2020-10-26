package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class BPSettingFinder {
	@Inject
	private BPSettingRepository bpSettingRepository;

	public List<BPSettingDto> getAllBonusPaySetting() {
		String companyId = AppContexts.user().companyId();
		List<BonusPaySetting> lstBonusPaySetting = this.bpSettingRepository.getAllBonusPaySetting(companyId);
		return lstBonusPaySetting.stream().map(c -> toBPSettingDto(c)).collect(Collectors.toList());
	}

	public BPSettingDto getBonusPaySetting(String bonusPaySettingCode) {
		String companyId = AppContexts.user().companyId();
		Optional<BonusPaySetting> bonusPaySetting = this.bpSettingRepository.getBonusPaySetting(companyId,
				new BonusPaySettingCode(bonusPaySettingCode));
		if (bonusPaySetting.isPresent()) {
			BonusPaySetting bPaySetting = bonusPaySetting.get();
			return new BPSettingDto(bPaySetting.getCode().v(), bPaySetting.getName().v());
		}

		return null;
	}

	private BPSettingDto toBPSettingDto(BonusPaySetting bonusPaySetting) {
		return new BPSettingDto(bonusPaySetting.getCode().toString(), bonusPaySetting.getName().toString());
	}
}
