package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.bonuspay.repository.PSBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.PersonalBonusPaySetting;

@Stateless
@Transactional
public class PSBonusPaySettingFinder {
	@Inject
	private PSBonusPaySettingRepository psBonusPaySettingRepository;

	public List<PSBonusPaySettingDto> getListSetting(List<String> lstEmployeeId) {
		List<PersonalBonusPaySetting> lstPersonalBonusPaySetting = this.psBonusPaySettingRepository
				.getListSetting(lstEmployeeId);
		return lstPersonalBonusPaySetting.stream().map(c -> toPSBonusPaySettingDto(c)).collect(Collectors.toList());
	}

	private PSBonusPaySettingDto toPSBonusPaySettingDto(PersonalBonusPaySetting personalBonusPaySetting) {

		return new PSBonusPaySettingDto(personalBonusPaySetting.getEmployeeId().toString(),
				personalBonusPaySetting.getBonusPaySettingCode().toString());
	}

}
