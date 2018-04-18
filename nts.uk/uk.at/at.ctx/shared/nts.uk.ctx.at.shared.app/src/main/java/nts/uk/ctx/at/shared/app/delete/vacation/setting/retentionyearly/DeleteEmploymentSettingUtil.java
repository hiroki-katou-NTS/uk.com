package nts.uk.ctx.at.shared.app.delete.vacation.setting.retentionyearly;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class DeleteEmploymentSettingUtil {
	/** The repository. */
	@Inject
	private EmploymentSettingRepository repository;
	
	public void delete(String employmentCode) {
		// get login user info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companyId by user login
		String companyId = loginUserContext.companyId();
		
		this.repository.remove(companyId, employmentCode);
	}
}
