package nts.uk.cnv.app.cnv.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionRecord;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionRecordRepository;

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
				command.getExplanation()
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
