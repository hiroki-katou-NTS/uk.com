package nts.uk.ctx.at.request.app.find.setting.company.mailsetting.remandsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMail;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMailRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ContentOfRemandMailFinder {
	@Inject
	private ContentOfRemandMailRepository mailRep;
	
	public ContentOfRemandMailDto findByCom(){
		String cid = AppContexts.user().companyId();
		Optional<ContentOfRemandMail> content = mailRep.getRemandMailById(cid);
		if(content.isPresent()){
			return ContentOfRemandMailDto.convertDto(content.get());
		}
		return null;
	}
}
