package nts.uk.ctx.pr.proto.app.layout.detail.command;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;

@RequestScoped
@Transactional
public class UpdateLayoutDetailCommandHandler extends CommandHandler<UpdateLayoutDetailCommand> {

	@Inject
	private LayoutMasterDetailRepository repository;
	@Override
	protected void handle(CommandHandlerContext<UpdateLayoutDetailCommand> context) {
		this.repository.update(context.getCommand().toDomain());
	}
	
}
