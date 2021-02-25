package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.PSBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.PersonalBonusPaySetting;


@Stateless
@Transactional
public class PSBonusPaySettingFinder {
	@Inject
	private PSBonusPaySettingRepository repo;

	public List<PSBonusPaySettingDto> getListSetting(List<String> lstEmployeeId) {
		List<PersonalBonusPaySetting> lstPersonalBonusPaySetting = this.repo
				.getListSetting(lstEmployeeId.stream().collect(Collectors.toList()));
		return lstPersonalBonusPaySetting.stream().map(c -> toPSBonusPaySettingDto(c)).collect(Collectors.toList());
	}

	public PSBonusPaySettingDto getPersonalBonusPaySetting(String employeeId) {
		Optional<PersonalBonusPaySetting> domain = repo.getPersonalBonusPaySetting(employeeId);
		if (domain.isPresent()) {
			return this.toPSBonusPaySettingDto(domain.get());
		}
		return null;
	}

	private PSBonusPaySettingDto toPSBonusPaySettingDto(PersonalBonusPaySetting personalBonusPaySetting) {

		return new PSBonusPaySettingDto(personalBonusPaySetting.getEmployeeId().toString(),
				personalBonusPaySetting.getBonusPaySettingCode().toString());
	}

}
