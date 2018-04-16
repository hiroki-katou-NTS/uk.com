package nts.uk.ctx.at.shared.app.delete.vacation.setting.subst;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class DeleteSubstVacationUtil {
	/** The emp sv repository. */
	@Inject
	private EmpSubstVacationRepository empSvRepository;
	
	public void delete(String employmentCode) {
		// get login user info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companyId by user login
		String companyId = loginUserContext.companyId();
		
		this.empSvRepository.delete(companyId, employmentCode);
	}
}
