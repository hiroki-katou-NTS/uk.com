package nts.uk.cnv.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.dom.conversiontable.ConversionSourcesRepository;

@Stateless
public class DeleteConversionSourceCommandHandler  extends CommandHandler<String>{

	@Inject
	ConversionSourcesRepository repository;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		String sourceId = context.getCommand();

		repository.delete(sourceId);
	}

}
