package nts.uk.cnv.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.cnv.app.dto.AddSourceResult;
import nts.uk.cnv.dom.conversiontable.ConversionSource;
import nts.uk.cnv.dom.conversiontable.ConversionSourcesRepository;

@Stateless
public class RegistConversionSourceCommandHandler extends CommandHandlerWithResult<RegistConversionSourceCommand, AddSourceResult>{

	@Inject
	ConversionSourcesRepository repository;

	@Override
	protected AddSourceResult handle(CommandHandlerContext<RegistConversionSourceCommand> context) {
		RegistConversionSourceCommand command = context.getCommand();

		String sourceId = repository.insert(new ConversionSource(
				command.getSourceId(),
				command.getCategory(),
				command.getSourceTableName(),
				command.getCondition(),
				command.getMemo()
			));

		return new AddSourceResult(sourceId);
	}

}
