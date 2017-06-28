package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.WorkplaceId;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkplaceBonusPaySetting;

@Stateless
@Transactional
public class WPBonusPaySettingFinder {
	@Inject
	private WPBonusPaySettingRepository wpBonusPaySettingRepository;

	public List<WPBonusPaySettingDto> getListSetting(List<String> lstWorkplace) {
		List<WorkplaceBonusPaySetting> lstWorkplaceBonusPaySetting = this.wpBonusPaySettingRepository
				.getListSetting(lstWorkplace.stream().map(c -> new WorkplaceId(c)).collect(Collectors.toList()));
		return lstWorkplaceBonusPaySetting.stream().map(c -> toWPBonusPaySettingDto(c)).collect(Collectors.toList());
	}

	public WPBonusPaySettingDto getWPBPSetting(String WorkplaceId) {
		Optional<WorkplaceBonusPaySetting> workplaceBonusPaySetting = this.wpBonusPaySettingRepository
				.getWPBPSetting(new WorkplaceId(WorkplaceId));
		return this.toWPBonusPaySettingDto(workplaceBonusPaySetting.get());
	}

	private WPBonusPaySettingDto toWPBonusPaySettingDto(WorkplaceBonusPaySetting workplaceBonusPaySetting) {
		return new WPBonusPaySettingDto(workplaceBonusPaySetting.getWorkplaceId().toString(),
				workplaceBonusPaySetting.getBonusPaySettingCode().toString());
	}

}
