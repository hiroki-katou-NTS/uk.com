package nts.uk.cnv.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.dom.conversiontable.ConversionTableRepository;
import nts.uk.cnv.dom.conversiontable.OneColumnConversion;
import nts.uk.cnv.dom.conversiontable.pattern.ConversionPatternFactory;

@Stateless
public class RegistConversionTableCommandHandler extends CommandHandler<RegistConversionTableCommand>{

	@Inject
	ConversionTableRepository repository;

	@Override
	protected void handle(CommandHandlerContext<RegistConversionTableCommand> context) {
		RegistConversionTableCommand command = context.getCommand();

		OneColumnConversion domain = new OneColumnConversion(
				command.getTargetColumn(),
				command.getConversionType(),
				ConversionPatternFactory.create(command));

		boolean exists = repository.isExists(command.getCategory(),
				command.getTable(),
				command.getRecordNo(),
				command.getTargetColumn());

		if (exists) {
			repository.delete(command.getCategory(),
					command.getTable(),
					command.getRecordNo(),
					command.getTargetColumn());
		}

		repository.insert(
				command.getCategory(),
				command.getTable(),
				command.getRecordNo(),
				domain);
	}

}
