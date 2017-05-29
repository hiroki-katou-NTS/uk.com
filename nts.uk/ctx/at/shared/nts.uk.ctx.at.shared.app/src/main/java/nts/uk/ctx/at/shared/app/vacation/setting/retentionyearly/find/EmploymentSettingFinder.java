package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find.dto.EmploymentSettingFindDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class EmploymentSettingFinder {

	@Inject
	private EmploymentSettingRepository repository;

	public EmploymentSettingFindDto find(String employmentCode) {
		// get login user info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companycode by user login
		String companyId = loginUserContext.companyCode();

		EmploymentSettingFindDto outputData = new EmploymentSettingFindDto();

		Optional<EmploymentSetting> employmentSetting = this.repository.find(companyId,
				employmentCode);

		if (employmentSetting.isPresent()) {
			employmentSetting.get().saveToMemento(outputData);
			return outputData;
		}
		return null;
	}
}
