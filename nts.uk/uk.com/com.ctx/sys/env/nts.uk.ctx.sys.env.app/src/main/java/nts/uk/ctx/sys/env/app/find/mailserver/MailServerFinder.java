package nts.uk.ctx.sys.env.app.find.mailserver;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.env.dom.mailserver.MailServer;
import nts.uk.ctx.sys.env.dom.mailserver.MailServerRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class MailServerFinder {
	@Inject
	private MailServerRepository repository;
	
	/**
	 * Find mail server setting.
	 *
	 * @return mail server setting
	 */
	public MailServerDto find(){
		
		// get login info
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();

	    Optional<MailServer> optMailServer = this.repository.findBy(companyId);
		
		if(!optMailServer.isPresent()){
			return null;
		}
		
		MailServerDto dto = new MailServerDto();
		optMailServer.get().saveToMemento(dto);
		
		return dto;
	}
}
