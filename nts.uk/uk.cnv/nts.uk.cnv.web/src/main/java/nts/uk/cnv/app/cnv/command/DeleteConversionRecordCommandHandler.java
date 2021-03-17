package nts.uk.cnv.app.cnv.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionRecordRepository;

@Stateless
public class DeleteConversionRecordCommandHandler extends CommandHandler<DeleteConversionRecordCommand> {

	@Inject
	ConversionRecordRepository repository;

	@Override
	protected void handle(CommandHandlerContext<DeleteConversionRecordCommand> context) {
		DeleteConversionRecordCommand command = context.getCommand();

		repository.delete(command.getCategory(), command.getTable(), command.getRecordNo());
	}

}
