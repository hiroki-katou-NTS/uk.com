package nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailcontenturlsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbedded;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbeddedRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UrlEmbeddedFinder {
	@Inject
	private UrlEmbeddedRepository urlRep;
	public UrlEmbeddedDto findByComId(){
		String cid = AppContexts.user().companyId();
		Optional<UrlEmbedded> mail = urlRep.getUrlEmbeddedById(cid);
		if(mail.isPresent()){
			return new UrlEmbeddedDto(mail.get().getUrlEmbedded().value);
		}
		return null;
	}
}
