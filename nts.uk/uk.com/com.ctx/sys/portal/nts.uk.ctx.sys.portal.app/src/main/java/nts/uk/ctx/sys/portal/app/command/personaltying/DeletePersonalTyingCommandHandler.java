package nts.uk.ctx.sys.portal.app.command.personaltying;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.sys.portal.dom.webmenu.personaltying.PersonalTyingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class DeletePersonalTyingCommandHandler {
	@Inject
	private PersonalTyingRepository respository;
	protected void handle(){
	String companyId = AppContexts.user().companyId();
	respository.delete(companyId);
	}
}
