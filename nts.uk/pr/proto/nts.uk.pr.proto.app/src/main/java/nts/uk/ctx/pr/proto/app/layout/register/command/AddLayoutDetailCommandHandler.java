package nts.uk.ctx.pr.proto.app.layout.register.command;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;

@RequestScoped
@Transactional
public class AddLayoutDetailCommandHandler extends CommandHandler<LayoutDetailCommand> {
	@Inject
	private LayoutMasterDetailRepository repository;
	@Override
	protected void handle(CommandHandlerContext<LayoutDetailCommand> context) {
		//this.repository.add(context.getCommand().toDomain());
	}

		
}
