package nts.uk.cnv.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.dom.conversiontable.ConversionRecordRepository;

@Stateless
public class SwapConversionRecordCommandHandler extends CommandHandler<SwapConversionRecordCommand> {

	@Inject
	ConversionRecordRepository repository;

	@Override
	protected void handle(CommandHandlerContext<SwapConversionRecordCommand> context) {
		SwapConversionRecordCommand command = context.getCommand();

		repository.swap(command.getCategory(), command.getTable(), command.getRecordNo1(), command.getRecordNo2());

	}

}
