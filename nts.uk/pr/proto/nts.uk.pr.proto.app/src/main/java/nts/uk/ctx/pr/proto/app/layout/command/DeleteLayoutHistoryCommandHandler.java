package nts.uk.ctx.pr.proto.app.layout.command;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;


import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.app.company.command.RemoveCompanyCommandHandler;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;

@RequestScoped
@Transactional
public class DeleteLayoutHistoryCommandHandler extends CommandHandler<RemoveCompanyCommandHandler> {

	
	@Inject
	private LayoutMasterRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveCompanyCommandHandler> context) {

		
	}

}
