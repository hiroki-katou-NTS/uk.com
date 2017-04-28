/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.titlemenu;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.titlemenu.TitleMenuRepository;

@Stateless
@Transactional
public class UpdateTitleMenuCommandHandler extends CommandHandler<UpdateTitleMenuCommand>{

	@Inject
	private TitleMenuRepository repository;

	@Override
	protected void handle(CommandHandlerContext<UpdateTitleMenuCommand> context) {
		repository.update(context.getCommand().toDomain());
	}
}
