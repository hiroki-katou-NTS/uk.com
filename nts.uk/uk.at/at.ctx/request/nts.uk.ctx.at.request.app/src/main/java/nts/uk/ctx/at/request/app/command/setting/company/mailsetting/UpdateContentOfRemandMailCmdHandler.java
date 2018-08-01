package nts.uk.ctx.at.request.app.command.setting.company.mailsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMail;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMailRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateContentOfRemandMailCmdHandler extends CommandHandler<ContentOfRemandMailCmd>{
	@Inject
	private ContentOfRemandMailRepository mailRep;

	@Override
	protected void handle(CommandHandlerContext<ContentOfRemandMailCmd> context) {
		String companyId = AppContexts.user().companyId();
		ContentOfRemandMailCmd data = context.getCommand();
		Optional<ContentOfRemandMail> mail = mailRep.getRemandMailById(companyId);
		ContentOfRemandMail dataInsert = Optional.of(data).map(m -> new ContentOfRemandMail(companyId, m.getMailTitle(), m.getMailBody())).get();
		if(mail.isPresent()){
			mailRep.update(dataInsert);
			return;
		}
		mailRep.add(dataInsert);
	}
}
