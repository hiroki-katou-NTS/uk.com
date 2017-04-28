package nts.uk.ctx.sys.portal.app.command.layout;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.layout.LayoutRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author LamDT
 */
@Stateless
@Transactional
public class CreateLayoutCommandHandler extends CommandHandler<CreateLayoutCommand> {

	@Inject
	private LayoutRepository layoutRepository;

	@Override
	protected void handle(CommandHandlerContext<CreateLayoutCommand> context) {
		// User context
		LoginUserContext loginInfo = AppContexts.user();
		String companyID = loginInfo.companyID();
		
		// Command
		CreateLayoutCommand command = context.getCommand();
		
		Layout layout = Layout.createFromJavaType(companyID, IdentifierUtil.randomUniqueId(), command.getPgType());
		layoutRepository.add(layout);
	}

}