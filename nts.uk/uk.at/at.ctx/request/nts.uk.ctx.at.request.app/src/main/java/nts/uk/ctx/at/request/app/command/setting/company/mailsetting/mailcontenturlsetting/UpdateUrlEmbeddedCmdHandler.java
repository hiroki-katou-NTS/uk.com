package nts.uk.ctx.at.request.app.command.setting.company.mailsetting.mailcontenturlsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbedded;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbeddedRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateUrlEmbeddedCmdHandler extends CommandHandler<UrlEmbeddedCmd>{
	@Inject
	private UrlEmbeddedRepository mailRep;
	
	@Override
	protected void handle(CommandHandlerContext<UrlEmbeddedCmd> context) {
		String companyId = AppContexts.user().companyId();
		UrlEmbeddedCmd data = context.getCommand();
		Optional<UrlEmbedded> mail = mailRep.getUrlEmbeddedById(companyId);
		UrlEmbedded dataInsert = Optional.of(data).map(m -> UrlEmbedded.createFromJavaType(companyId, m.getUrlEmbedded())).get();
		if(mail.isPresent()){
			mailRep.update(dataInsert);
			return;
		}
		mailRep.add(dataInsert);
	}

}
