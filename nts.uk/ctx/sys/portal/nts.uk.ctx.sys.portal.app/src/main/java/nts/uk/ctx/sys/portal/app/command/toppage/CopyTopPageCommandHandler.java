package nts.uk.ctx.sys.portal.app.command.toppage;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;
import nts.uk.ctx.sys.portal.dom.toppage.service.TopPageService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CopyTopPageCommandHandler extends CommandHandler<CopyTopPageCommand>{

	/** The top page repository. */
	@Inject
	TopPageRepository topPageRepository;
	
	/** The Top page service. */
	@Inject
	TopPageService topPageService;
	
	@Override
	protected void handle(CommandHandlerContext<CopyTopPageCommand> context) {
		CopyTopPageCommand command = context.getCommand();
		String companyId = AppContexts.user().companyID();
		boolean isCheckOverWrite = command.isCheckOverwrite;
		String copyCode = command.getCopyCode();
		TopPage tp = command.toDomain();
		topPageService.copyTopPage(tp, companyId, isCheckOverWrite, copyCode);
	}

}
