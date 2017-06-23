package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.bonuspay.repository.CPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.CompanyBonusPaySetting;

@Stateless
@Transactional
public class CompanyBPSettingFinder {
	@Inject
	private CPBonusPaySettingRepository cpBonusPaySettingRepository;

	public CompanyBPSettingDto getSetting(String companyId) {
		Optional<CompanyBonusPaySetting> companyBonusPaySetting = cpBonusPaySettingRepository.getSetting(companyId);
		return this.toCompanyBPSettingDto(companyBonusPaySetting.get());

	}

	private CompanyBPSettingDto toCompanyBPSettingDto(CompanyBonusPaySetting companyBonusPaySetting) {
		return new CompanyBPSettingDto(companyBonusPaySetting.getCompanyId().toString(),
				companyBonusPaySetting.getBonusPaySettingCode().toString());
	}

}
