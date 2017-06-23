package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkplaceBonusPaySetting;

@Stateless
@Transactional
public class WPBonusPaySettingFinder {
	@Inject
	private WPBonusPaySettingRepository wpBonusPaySettingRepository;

	public List<WPBonusPaySettingDto> getListSetting(List<String> lstWorkplace) {
		List<WorkplaceBonusPaySetting> lstWorkplaceBonusPaySetting = this.wpBonusPaySettingRepository
				.getListSetting(lstWorkplace);
		return lstWorkplaceBonusPaySetting.stream().map(c -> toWPBonusPaySettingDto(c)).collect(Collectors.toList());
	}

	private WPBonusPaySettingDto toWPBonusPaySettingDto(WorkplaceBonusPaySetting workplaceBonusPaySetting) {
		return new WPBonusPaySettingDto(workplaceBonusPaySetting.getWorkplaceId().toString(),
				workplaceBonusPaySetting.getBonusPaySettingCode().toString());
	}

}
