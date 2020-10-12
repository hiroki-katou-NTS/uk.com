package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.CPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.CompanyBonusPaySetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class CompanyBPSettingFinder {
	@Inject
	private CPBonusPaySettingRepository repo;

	public CompanyBPSettingDto getSetting() {
		String companyId = AppContexts.user().companyId();

		Optional<CompanyBonusPaySetting> domain = repo.getSetting(companyId);

		if (domain.isPresent()) {
			return this.toCompanyBPSettingDto(domain.get());
		}

		return null;
	}

	private CompanyBPSettingDto toCompanyBPSettingDto(CompanyBonusPaySetting companyBonusPaySetting) {
		return new CompanyBPSettingDto(companyBonusPaySetting.getCompanyId().toString(),
				companyBonusPaySetting.getBonusPaySettingCode().toString());
	}

}
