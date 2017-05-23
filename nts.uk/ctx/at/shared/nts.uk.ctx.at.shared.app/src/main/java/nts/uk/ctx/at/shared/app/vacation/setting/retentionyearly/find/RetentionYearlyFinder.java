package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.find.dto.RetentionYearlyFindDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class RetentionYearlyFinder {

	@Inject
	private RetentionYearlySettingRepository repository;
	
	public RetentionYearlyFindDto findById(String companyId) {
		// get login user info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companycode by user login
		String companyCode = loginUserContext.companyCode();
		
		RetentionYearlyFindDto outputData = new RetentionYearlyFindDto();
		
		Optional<RetentionYearlySetting> data = this.repository.findByCompanyId(companyCode);
		if(data.isPresent()) {
			data.get().saveToMemento(outputData);
			return outputData;
		}
		return null;
	}
}
