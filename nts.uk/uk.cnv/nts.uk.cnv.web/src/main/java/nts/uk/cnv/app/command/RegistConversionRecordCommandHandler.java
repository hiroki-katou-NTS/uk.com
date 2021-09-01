package nts.uk.cnv.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.core.dom.conversiontable.ConversionRecord;
import nts.uk.cnv.dom.conversiontable.ConversionRecordRepository;

@Stateless
public class RegistConversionRecordCommandHandler extends CommandHandler<RegistConversionRecordCommand> {

	@Inject
	ConversionRecordRepository repository;

	@Override
	protected void handle(CommandHandlerContext<RegistConversionRecordCommand> context) {
		RegistConversionRecordCommand command =context.getCommand();

		ConversionRecord domain = new ConversionRecord(
				command.getCategory(),
				command.getTable(),
				command.getRecordNo(),
				command.getSourceId(),
				command.getExplanation(),
				command.isRemoveDuplicate()
			);

		boolean exists = repository.isExists(command.getCategory(),
				command.getTable(),
				command.getRecordNo());

		if (exists) {
			repository.update(domain);
			return;
		}

		repository.insert(domain);
	}

}
