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
public class CreateTitleMenuCommandHandler extends CommandHandler<CreateTitleMenuCommand> {

	@Inject
	public TitleMenuRepository repository;

	@Override
	protected void handle(CommandHandlerContext<CreateTitleMenuCommand> context) {
			repository.add(context.getCommand().toDomain());
	}
}
